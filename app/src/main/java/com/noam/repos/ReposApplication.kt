package com.noam.repos

import android.app.Application
import com.noam.repos.di.appModule
import com.noam.repos.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ReposApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@ReposApplication)
            modules(appModule, databaseModule)
        }
    }
}