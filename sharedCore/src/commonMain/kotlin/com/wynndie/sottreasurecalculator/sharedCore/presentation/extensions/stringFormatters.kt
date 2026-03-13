package com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions

fun String.formatAsAmount(): String {
    return this.replace(Regex("\\B(?=(\\d{3})+(?!\\d))"), " ")
}