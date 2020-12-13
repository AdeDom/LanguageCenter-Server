package com.lc.server.util

import com.google.gson.Gson
import com.lc.server.business.jwtconfig.UserPrincipal
import io.ktor.application.*
import io.ktor.auth.*

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)

val ApplicationCall.userId
    get() = authentication.principal<UserPrincipal>()?.userId
