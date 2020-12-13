package com.lc.server.business.jwtconfig

import io.ktor.auth.*

data class UserPrincipal(val userId: String? = null) : Principal
