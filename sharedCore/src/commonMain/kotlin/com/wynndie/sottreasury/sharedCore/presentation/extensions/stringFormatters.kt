package com.wynndie.sottreasury.sharedCore.presentation.extensions

fun String.formatAsAmount(): String {
    return this.replace(Regex("\\B(?=(\\d{3})+(?!\\d))"), " ")
}

fun String.getInitials(): String {
    return this.split(" ").map { it.first() }.joinToString("")
}