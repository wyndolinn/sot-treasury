package com.wynndie.sottreasury

import android.app.Application
import com.wynndie.sottreasury.di.initKoin
import org.koin.android.ext.koin.androidContext

class SotApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@SotApplication)
        }
    }
}