package com.sberg413.rickandmorty

import android.app.Application
import com.sberg413.rickandmorty.di.AppModule
import com.sberg413.rickandmorty.di.RepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

class RickMortyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@RickMortyApplication)
            modules(*KOIN_MODULE_ARRAY )
        }
    }

    companion object {

        private val KOIN_MODULE_ARRAY : Array<Module> = arrayOf(AppModule, RepositoryModule)
    }
}