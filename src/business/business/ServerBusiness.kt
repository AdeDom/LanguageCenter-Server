package com.lc.server.business.business

interface ServerBusiness {

    fun isValidateJwtIncorrect(token: String): Boolean

    fun isValidateJwtExpires(token: String): Boolean

}
