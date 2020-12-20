package com.lc.server.models.response

data class BaseResponse(
    var success: Boolean = false,
    var message: String? = null,
)
