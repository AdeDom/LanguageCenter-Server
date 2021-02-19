package com.lc.server.business.auth

import com.lc.server.business.business.ServerBusiness
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.data.repository.ServerRepository
import com.lc.server.getHttpClientApache
import com.lc.server.models.model.Token
import com.lc.server.models.request.RefreshTokenRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.request.ValidateTokenRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.SignInResponse
import com.lc.server.util.LanguageCenterConstant
import com.lc.server.util.fromJson
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.locations.*
import models.response.GoogleApiTokenResponse
import models.response.GoogleApiUserInfoResponse

@KtorExperimentalLocationsAPI
internal class AuthServiceImpl(
    private val repository: ServerRepository,
    private val jwtConfig: JwtConfig,
    private val business: ServerBusiness,
) : AuthService {

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
                    accessToken = jwtConfig.makeAccessToken(googleApiUserInfoResponse.id),
                    refreshToken = jwtConfig.makeRefreshToken(googleApiUserInfoResponse.id),
                )

                response.token = token
                response.success = repository.signIn(googleApiUserInfoResponse)
                googleApiUserInfoResponse.id?.let { response.isUpdateProfile = repository.isUpdateProfile(it) }
                "Sign in success"
            }
        }

        response.message = message
        return response
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Pair<HttpStatusCode, SignInResponse> {
        var httpStatusCode = HttpStatusCode.OK
        val response = SignInResponse()
        val (refreshToken) = refreshTokenRequest

        val message: String = when {
            // validate Null Or Blank
            refreshToken.isNullOrBlank() -> "Null"

            // validate values of variable
            business.isValidateJwtIncorrect(refreshToken) -> "Incorrect"
            business.isValidateJwtExpires(refreshToken) -> {
                httpStatusCode = HttpStatusCode.Unauthorized
                "Incorrect"
            }

            // validate database

            // execute
            else -> {
                val userId = jwtConfig.decodeJwtGetUserId(refreshToken)
                val token = Token(
                    accessToken = jwtConfig.makeAccessToken(userId),
                    refreshToken = jwtConfig.makeRefreshToken(userId),
                )
                response.token = token
                response.success = true
                "Refresh token success"
            }
        }

        response.message = message
        return Pair(httpStatusCode, response)
    }

    override fun validateToken(validateTokenRequest: ValidateTokenRequest): BaseResponse {
        val response = BaseResponse()
        val (_, refreshToken) = validateTokenRequest

        val message: String = when {
            // validate Null Or Blank
            refreshToken.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable
            jwtConfig.isValidateToken(refreshToken) -> "isValidateToken"

            // execute
            else -> {
                response.success = true
                "OK"
            }
        }

        response.message = message
        return response
    }

}
