package com.wynndie.sottreasury.sharedCore.domain.validation

import com.wynndie.sottreasury.sharedCore.domain.outcome.ValidationError

interface Validator<T> {
    fun validate(value: T): Pair<Boolean, ValidationError?>
}