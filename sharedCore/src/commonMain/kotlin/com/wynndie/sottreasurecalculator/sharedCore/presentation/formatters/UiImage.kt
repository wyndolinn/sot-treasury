package com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

sealed interface UiImage {

    data class ResourceImage(val id: DrawableResource) : UiImage {
        @Composable
        override fun asPainter(): Painter = painterResource(id)
    }

    data class VectorImage(val vector: ImageVector) : UiImage {
        @Composable
        override fun asPainter(): VectorPainter = rememberVectorPainter(vector)
    }

    data class UrlImage(val value: String) : UiImage {
        @Composable
        override fun asPainter(): AsyncImagePainter = rememberAsyncImagePainter(value)
    }

    @Composable
    fun asPainter(): Painter
}