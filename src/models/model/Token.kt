package com.lc.server.models.model

data class Token(
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
