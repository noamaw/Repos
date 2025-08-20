package com.noam.repos.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.noam.repos.db.GitRepositoryDatabase
import com.noam.repos.model.FavoriteRepositoriesRepository
import com.noam.repos.model.FavoriteRepositoriesRepositoryImpl
import com.noam.repos.model.RepositoriesRepository
import com.noam.repos.model.RepositoriesRepositoryImpl
import com.noam.repos.network.KtorClient
import com.noam.repos.viewmodel.FavoriteRepositoriesViewModel
import com.noam.repos.viewmodel.RepositoriesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { KtorClient() }
    single { RepositoriesRepositoryImpl(get()) } bind RepositoriesRepository::class
    viewModel { RepositoriesViewModel(get()) }

    single { FavoriteRepositoriesRepositoryImpl(get()) } bind FavoriteRepositoriesRepository::class
    viewModel { FavoriteRepositoriesViewModel(get()) }
}

val databaseModule = module {
    single { provideDatabase(androidContext()) } // Provides the AppDatabase instance
    single { get<GitRepositoryDatabase>().gitRepositoryDao() } // Provides the DAO instance
}

fun provideDatabase(context: Context): GitRepositoryDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        GitRepositoryDatabase::class.java,
        "my_database.db"
    ) .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}