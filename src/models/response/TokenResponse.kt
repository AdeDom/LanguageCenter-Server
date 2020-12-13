package com.lc.server.models.response

data class TokenResponse(
    var success: Boolean = false,
    var message: String? = null,
    val accessToken: String? = null,
)
