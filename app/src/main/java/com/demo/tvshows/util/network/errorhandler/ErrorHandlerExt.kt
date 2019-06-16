package com.demo.tvshows.util.network.errorhandler

import com.demo.tvshows.data.remote.response.BaseResponse
import io.reactivex.Single

fun <T : BaseResponse> Single<T>.handleError(errorHandler: ErrorHandler): Single<T> {
    return onErrorResumeNext { Single.error(errorHandler.parseException(it)) }
}
