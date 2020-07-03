package com.demo.tvshows.util.network.errorhandler

interface ErrorHandler {
    fun handle(responseBody: String?): ServiceException
    fun canHandle(errorCode: Int): Boolean
}
