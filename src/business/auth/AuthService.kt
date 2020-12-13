package com.lc.server.business.auth

import com.lc.server.models.request.RefreshTokenRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import io.ktor.http.*
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface AuthService {

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Pair<HttpStatusCode, SignInResponse>

}
