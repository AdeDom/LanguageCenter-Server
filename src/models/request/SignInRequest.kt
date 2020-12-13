package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/auth/sign-in")
data class SignInRequest(
    val serverAuthCode: String? = null,
)
