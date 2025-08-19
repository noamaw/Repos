package com.noam.repos.di

import com.noam.repos.model.RepositoriesRepository
import com.noam.repos.model.RepositoriesRepositoryImpl
import com.noam.repos.network.KtorClient
import com.noam.repos.viewmodel.RepositoriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { KtorClient() }
    factory<RepositoriesRepository> { RepositoriesRepositoryImpl(get()) }
    viewModel { RepositoriesViewModel(get()) }
}