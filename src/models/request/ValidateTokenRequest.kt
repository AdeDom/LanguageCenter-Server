package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/auth/validate-token/{token}")
data class ValidateTokenRequest(
    val token: String? = null,
    val refreshToken: String? = null,
)
