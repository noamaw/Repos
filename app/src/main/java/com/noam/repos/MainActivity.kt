package com.noam.repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.noam.repos.models.TimeFrame
import com.noam.repos.network.KtorClient
import com.noam.repos.ui.theme.ReposTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ktorClient = KtorClient()
        setContent {
            ReposTheme {
                LaunchedEffect(key1 = ktorClient) {
                    withContext(Dispatchers.IO) {
                        ktorClient.getRepositories(timeframe = TimeFrame.LastDay).onSuccess {
                            println("Repositories fetched successfully: $it")
                        }.onFailure {
                            println("Error fetching repositories: ${it.message}")
                        }

                        delay(2000)
                        ktorClient.getNextPage().onSuccess {
                            println("Next page fetched successfully: $it")
                        }.onFailure {
                            println("Error fetching next page: ${it.message}")
                        }
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReposTheme {
        Greeting("Android")
    }
}