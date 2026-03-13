package com.wynndie.sottreasurecalculator.sharedCore.domain.validation

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.ValidationError

interface Validator<T> {
    fun validate(value: T): Pair<Boolean, ValidationError?>
}