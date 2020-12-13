package com.lc.server.business.auth

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.TokenResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
class AuthServiceImpl : AuthService {

    override fun signIn(signInRequest: SignInRequest): TokenResponse {
        val response = TokenResponse()
        return response
    }

}
