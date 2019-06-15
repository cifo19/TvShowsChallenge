package com.demo.tvshows.util.network.errorhandler

sealed class ServiceResultException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)
class UnHandledServiceException(cause: Throwable? = null) : ServiceResultException(cause = cause)
class HandledServiceException(message: String) : ServiceResultException(message)
class NoConnectionException(cause: String) : ServiceResultException(cause)