package com.wynndie.sottreasurecalculator.domain.validation

import com.wynndie.sottreasurecalculator.domain.outcome.ValidationError

interface Validator<T> {
    fun validate(value: T): Pair<Boolean, ValidationError?>
}