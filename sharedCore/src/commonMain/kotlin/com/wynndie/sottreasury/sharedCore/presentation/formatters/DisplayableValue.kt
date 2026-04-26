package com.wynndie.sottreasury.sharedCore.presentation.formatters

interface DisplayableValue<T> {
    val value: Number
    val formatted: T
}