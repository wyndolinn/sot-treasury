package com.wynndie.sottreasurecalculator.presentation.formatters

interface DisplayableValue<T> {
    val value: Number
    val formatted: T
}