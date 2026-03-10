package com.wynndie.sottreasurecalculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform