package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.AppError
import io.ktor.client.features.ResponseException
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.cio.ChannelReadException
import kotlinx.coroutines.TimeoutCancellationException

fun Throwable.toAppError(): AppError {
    return when (this) {
        is AppError -> this
        is ResponseException ->
            return AppError.ApiException.ServerException(this)
        is ChannelReadException ->
            return AppError.ApiException.NetworkException(this)
        is TimeoutCancellationException, is SocketTimeoutException -> {
            AppError.ApiException
                .TimeoutException(this)
        }
        else -> AppError.UnknownException(this)
    }
}
