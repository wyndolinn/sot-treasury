package com.wynndie.sottreasury.sharedCore.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val corePlatformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
}