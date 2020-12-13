package com.lc.server.business.jwtconfig

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtConfigImpl : JwtConfig {

    private val algorithm = Algorithm.HMAC512(SECRET)

    override val verifier: JWTVerifier
        get() = JWT
            .require(algorithm)
            .withIssuer(ISSUER)
            .build()

    override val realm: String
        get() = REALM

    override val userId: String
        get() = USER_ID

    override fun makeAccessToken(userId: String?): String {
        return encodeJWTSetUserId(userId, Date(System.currentTimeMillis() + ACCESS_TOKEN))
    }

    override fun makeRefreshToken(userId: String?): String {
        return encodeJWTSetUserId(userId, Date(System.currentTimeMillis() + REFRESH_TOKEN))
    }

    private fun encodeJWTSetUserId(userId: String?, withExpiresAt: Date): String = JWT.create()
        .withSubject(SUBJECT)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim(USER_ID, userId)
        .withExpiresAt(withExpiresAt)
        .sign(algorithm)

    override fun decodeJwtGetUserId(token: String): String {
        return JWT().decodeJwt(token).getClaim(USER_ID).asString()
    }

    companion object {
        const val SUBJECT = "Authentication"
        const val SECRET = "bc162b7210edb9dae67b90"
        const val ISSUER = "ktor.io"
        const val AUDIENCE = "language-center"
        const val ACCESS_TOKEN = 60_000
        const val REFRESH_TOKEN = 36_000_00 * 24 * 7
        const val USER_ID = "user_id"
        const val REALM = "ktor.io"
    }

}
