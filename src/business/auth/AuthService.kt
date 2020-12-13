package com.lc.server.business.auth

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.TokenResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface AuthService {

    fun signIn(signInRequest: SignInRequest): TokenResponse

}
