package com.noam.repos.network

import com.noam.repos.model.TimeFrame
import com.noam.repos.model.domain.GitRepository
import com.noam.repos.model.domain.RepositoriesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.ZoneId
import io.ktor.http.parseHeaderValue


class KtorClient {
    private val client = HttpClient(CIO) {

        defaultRequest {
            url("https://api.github.com/search/")
            header("Accept", "application/json")
        }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        expectSuccess = true
    }

    private var nextPageCache = ""

    suspend fun getRepositories(timeframe: TimeFrame) : ApiOperation<List<GitRepository>> {
        val createdBy = formatToIso8601(
            when (timeframe) {
                TimeFrame.LastDay -> get1DayAgo()
                TimeFrame.LastWeek -> get1WeekAgo()
                TimeFrame.LastMonth -> get1MonthAgo()
                TimeFrame.Unknown -> get1MonthAgo()
            }
        )
        return safeApiCall {
            val response = client.get("repositories?") {
                url {
                    parameters.apply {
                        append("q", "created:>=$createdBy")
                        append("sort", "stars")
                        append("order", "desc")
                    }
                }
            }

            response.headers["Link"]?.let { updateNextPageCache(it) }

            response.body<RepositoriesResponse>().items
        }
    }

    suspend fun getNextPage(): ApiOperation<List<GitRepository>> {
        return safeApiCall {
            val response = client.get(nextPageCache.removePrefix("<").removeSuffix(">"))

            response.headers["Link"]?.let { updateNextPageCache(it) }

            response.body<RepositoriesResponse>().items
        }
    }

    private fun updateNextPageCache(nextPageCachelink: String) {
        val nextLink = parseHeaderValue(nextPageCachelink)
        nextPageCache = nextLink.find { it -> it.params.first().value == "next" }?.value ?: ""
        println("Updating next Page Cache Link: $nextPageCache")
    }

    private fun get1DayAgo(): LocalDate {
        return LocalDate.now(ZoneId.systemDefault()).minusDays(1)
    }

    private fun get1WeekAgo(): LocalDate {
        return LocalDate.now(ZoneId.systemDefault()).minusWeeks(1)
    }

    private fun get1MonthAgo(): LocalDate {
        return LocalDate.now(ZoneId.systemDefault()).minusMonths(1)
    }

    private fun formatToIso8601(date: LocalDate): String {
        return date.toString()
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }
}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    fun <R> mapSuccess(transform: (T) -> R): ApiOperation<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(exception)
        }
    }

    suspend fun onSuccess(block: suspend (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}