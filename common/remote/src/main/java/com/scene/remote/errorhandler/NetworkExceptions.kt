package com.scene.remote.errorhandler

import java.io.IOException

data class ServiceException(override val message: String? = null) : IOException(message)
data class NoConnectionException(override val message: String? = null) : IOException(message)
