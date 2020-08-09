package com.demo.tvshows.errorhandler

interface ErrorHandler {
    fun canHandle(errorCode: Int): Boolean
    fun handle(responseBody: String?): ServiceException
}
