package com.wynndie.sottreasury.sharedCore.presentation.extensions

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp

inline fun Modifier.thenIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) then(this.block()) else then(this)
}

inline fun Modifier.thenIf(
    condition: Boolean,
    onTrue: Modifier.() -> Modifier,
    onFalse: Modifier.() -> Modifier
): Modifier {
    return if (condition) then(this.onTrue()) else then(this.onFalse())
}

inline fun <T> Modifier.thenIfNull(value: T?, block: Modifier.() -> Modifier): Modifier {
    return if (value == null) then(this.block()) else then(this)
}

inline fun <T> Modifier.thenIfNotNull(value: T?, block: Modifier.(T) -> Modifier): Modifier {
    return if (value != null) then(this.block(value)) else then(this)
}


fun Modifier.tileShadow(shape: CornerBasedShape, color: Color = Color.Black): Modifier {
    return this.graphicsLayer {
        shadowElevation = 12.dp.toPx()
        this.shape = shape
        clip = false
    }
}