package com.lc.server.models.response

data class SendMessageResponse(
    var success: Boolean = false,
    var message: String? = null,
    var dateTimeLong: Long = 0,
)
