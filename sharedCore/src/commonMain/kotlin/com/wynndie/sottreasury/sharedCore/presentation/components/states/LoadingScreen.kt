package com.wynndie.sottreasury.sharedCore.presentation.components.states

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.wynndie.sottreasury.sharedResources.Res
import com.wynndie.sottreasury.sharedResources.img_wheel
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            rotation.animateTo(15f, tween(1200, easing = FastOutSlowInEasing))
            rotation.animateTo(240f, tween(1500, easing = FastOutSlowInEasing))
            rotation.animateTo(245f, tween(300, easing = FastOutLinearInEasing))
            rotation.animateTo(165f, tween(1000, easing = FastOutSlowInEasing))
            rotation.animateTo(170f, tween(300, easing = FastOutLinearInEasing))
            rotation.animateTo(-120f, tween(1500, easing = FastOutSlowInEasing))
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.img_wheel),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(96.dp)
                .graphicsLayer {
                    rotationZ = rotation.value
                }
        )
    }
}