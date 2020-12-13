package com.lc.server.business.auth

import com.lc.server.data.repository.ServerRepository
import com.lc.server.getHttpClientApache
import com.lc.server.models.model.Token
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.util.LanguageCenterConstant
import com.lc.server.util.fromJson
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.locations.*
import models.response.GoogleApiTokenResponse
import models.response.GoogleApiUserInfoResponse

@KtorExperimentalLocationsAPI
class AuthServiceImpl(private val repository: ServerRepository) : AuthService {

    override suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        val response = SignInResponse()
        val (serverAuthCode) = signInRequest

        val message: String = when {
            // validate Null Or Blank
            serverAuthCode.isNullOrBlank() -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val googleApiTokenResponse = getHttpClientApache().post<String>("https://oauth2.googleapis.com/token") {
                    body = MultiPartFormDataContent(formData {
                        append("grant_type", LanguageCenterConstant.grantTypeAccessToken)
                        append("code", serverAuthCode)
                        append("client_id", LanguageCenterConstant.clientId)
                        append("client_secret", LanguageCenterConstant.clientSecret)
                    })
                }.fromJson<GoogleApiTokenResponse>()

                val googleApiUserInfoResponse =
                    getHttpClientApache().get<String>("https://www.googleapis.com/userinfo/v2/me") {
                        header("Authorization", "Bearer ${googleApiTokenResponse.accessToken}")
                    }.fromJson<GoogleApiUserInfoResponse>()

                val token = Token(
                    accessToken = googleApiTokenResponse.idToken,
                    refreshToken = googleApiTokenResponse.refreshToken,
                )

                response.token = token
                response.success = repository.signIn(googleApiUserInfoResponse)
                "Sign in success"
            }
        }

        response.message = message
        return response
    }

}
