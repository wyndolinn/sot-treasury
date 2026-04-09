package com.wynndie.sottreasury.sharedCore.domain.outcome

typealias RootError = Error
typealias EmptyOutcome<E> = Outcome<Unit, E>

sealed interface Outcome<out D, out E : RootError> {
    data class Success<out D>(val data: D) : Outcome<D, Nothing>
    data class Error<out E : RootError>(val error: E) : Outcome<Nothing, E>
}

inline fun <D, E : RootError> Outcome<D, E>.onSuccess(action: (D) -> Unit): Outcome<D, E> {
    return when (this) {
        is Outcome.Error -> this
        is Outcome.Success -> {
            action(data)
            this
        }
    }
}

inline fun <D, E : RootError> Outcome<D, E>.onError(action: (E) -> Unit): Outcome<D, E> {
    return when (this) {
        is Outcome.Success -> this
        is Outcome.Error -> {
            action(error)
            this
        }
    }
}

inline fun <D, E : RootError, R> Outcome<D, E>.map(map: (D) -> R): Outcome<R, E> {
    return when (this) {
        is Outcome.Success -> Outcome.Success(map(data))
        is Outcome.Error -> Outcome.Error(error)
    }
}

inline fun <D, E : RootError> Outcome<List<D>, E>.findOrNull(predicate: (D) -> Boolean): D? {
    return when (this) {
        is Outcome.Success -> data.find(predicate)
        is Outcome.Error -> null
    }
}

inline fun <D, E : RootError> Outcome<D, E>.getOrElse(action: (E) -> D): D {
    return when (this) {
        is Outcome.Success -> data
        is Outcome.Error -> action(error)
    }
}

fun <D, E : RootError> Outcome<D, E>.getOrNull(): D? {
    return when (this) {
        is Outcome.Success -> data
        is Outcome.Error -> null
    }
}