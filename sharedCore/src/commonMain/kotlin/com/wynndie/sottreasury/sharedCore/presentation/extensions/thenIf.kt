package com.wynndie.sottreasury.sharedCore.presentation.extensions

import androidx.compose.ui.Modifier

inline fun Modifier.thenIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) then(this.block()) else then(this)
}

inline fun Modifier.thenIf(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier
): Modifier {
    return if (condition) then(this.ifTrue()) else then(this.ifFalse())
}

inline fun <T> Modifier.thenIfNull(value: T?, block: Modifier.() -> Modifier): Modifier {
    return if (value == null) then(this.block()) else then(this)
}

inline fun <T> Modifier.thenIfNotNull(value: T?, block: Modifier.(T) -> Modifier): Modifier {
    return if (value != null) then(this.block(value)) else then(this)
}