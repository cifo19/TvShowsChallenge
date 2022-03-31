package com.scene.data.errorhandler

interface ErrorHandler {
    fun canHandle(errorCode: Int): Boolean
    fun handle(responseBody: String?): ServiceException
}
