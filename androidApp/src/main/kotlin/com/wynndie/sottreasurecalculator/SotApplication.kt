package com.wynndie.sottreasurecalculator

import android.app.Application
import com.wynndie.sottreasurecalculator.di.initKoin
import org.koin.android.ext.koin.androidContext

class SotApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@SotApplication)
        }
    }
}