package com.wynndie.sottreasury.sharedCore.presentation.formatters

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Serializable
sealed interface UiText {

    data class DynamicString(val value: String) : UiText
    class ResourceString(
        val id: StringResource,
        vararg formatArgs: Any
    ) : UiText {
        val args: Array<out Any> = formatArgs
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is ResourceString -> {

                val newArgs = args.map {
                    when (it) {
                        is StringResource -> stringResource(it)
                        else -> it.toString()
                    }
                }.toTypedArray()

                stringResource(id, *newArgs)
            }
        }
    }

    suspend fun asStringAsync(): String {
        return when (this) {
            is DynamicString -> value
            is ResourceString -> {

                val newArgs = args.map {
                    when (it) {
                        is StringResource -> getString(it)
                        else -> it.toString()
                    }
                }.toTypedArray()

                return getString(id, *newArgs)
            }
        }
    }
}