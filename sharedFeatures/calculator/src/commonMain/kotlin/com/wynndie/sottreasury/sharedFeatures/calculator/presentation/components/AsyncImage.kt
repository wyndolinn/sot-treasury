package com.wynndie.sottreasury.sharedFeatures.calculator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.wynndie.sottreasury.sharedCore.presentation.extensions.shimmerEffect

@Composable
fun AsyncImage(
    url: String,
    contentDescription: String?,
    error: String,
    errorStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    errorColor: Color = MaterialTheme.colorScheme.onSurface,
    contentScale: ContentScale = ContentScale.Crop,
    contentSpacing: Dp = 0.dp,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        success = {
            Image(
                painter = it.painter,
                contentDescription = null,
                contentScale = contentScale,
                modifier = Modifier.padding(contentSpacing)
            )
        },
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
        },
        error = if (error.isNotBlank()) {
            {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error,
                        style = errorStyle,
                        color = errorColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else null,
        modifier = modifier
    )
}
