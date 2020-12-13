package com.lc.server.business.business

import com.auth0.jwt.JWT
import java.util.*

class ServerBusinessImpl : ServerBusiness {

    override fun isValidateJwtIncorrect(token: String): Boolean = try {
        JWT().decodeJwt(token).getClaim("user_id").asString() == null
    } catch (e: Throwable) {
        true
    }

    override fun isValidateJwtExpires(token: String): Boolean = try {
        JWT().decodeJwt(token).getClaim("exp").asDate() < Date()
    } catch (e: Throwable) {
        true
    }

}
