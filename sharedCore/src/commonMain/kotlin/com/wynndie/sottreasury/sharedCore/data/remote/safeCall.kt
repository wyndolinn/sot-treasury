package com.wynndie.sottreasury.sharedCore.data.remote

import com.wynndie.sottreasury.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasury.sharedCore.domain.outcome.Outcome
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Outcome<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (_: SocketTimeoutException) {
        return Outcome.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (_: UnresolvedAddressException) {
        return Outcome.Error(DataError.Remote.NO_INTERNET)
    } catch (_: Exception) {
        currentCoroutineContext().ensureActive()
        return Outcome.Error(DataError.Remote.UNKNOWN)
    }
    return response.toOutcome<T>()
}

suspend inline fun <reified T> HttpResponse.toOutcome(): Outcome<T, DataError.Remote> {
    return when (this.status.value) {
        in 200..299 -> {
            try {
                Outcome.Success(this.body<T>())
            } catch (_: NoTransformationFoundException) {
                Outcome.Error(DataError.Remote.SERIALIZATION_ERROR)
            }
        }

        400 -> Outcome.Error(DataError.Remote.BAD_REQUEST)
        401 -> Outcome.Error(DataError.Remote.UNAUTHORIZED)
        403 -> Outcome.Error(DataError.Remote.FORBIDDEN)
        404 -> Outcome.Error(DataError.Remote.NOT_FOUND)
        408 -> Outcome.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Outcome.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Outcome.Error(DataError.Remote.SERVER_ERROR)
        else -> Outcome.Error(DataError.Remote.UNKNOWN)
    }
}