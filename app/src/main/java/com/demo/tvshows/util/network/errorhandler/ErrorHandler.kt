package com.demo.tvshows.util.network.errorhandler

interface ErrorHandler {
    fun canHandle(errorCode: Int): Boolean
    fun handle(responseBody: String?): ServiceException
}
