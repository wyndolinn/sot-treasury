package com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters

interface DisplayableValue<T> {
    val value: Number
    val formatted: T
}