package com.scene.remote.errorhandler

interface ErrorHandler {
    fun canHandle(errorCode: Int): Boolean
    fun handle(responseBody: String?): ServiceException
}
