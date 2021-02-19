package com.lc.server.business.jwtconfig

import com.auth0.jwt.JWTVerifier

internal interface JwtConfig {

    val verifier: JWTVerifier

    val realm: String

    val userId: String

    fun makeAccessToken(userId: String?): String

    fun makeRefreshToken(userId: String?): String

    fun decodeJwtGetUserId(token: String): String

    fun isValidateToken(token: String): Boolean

}
