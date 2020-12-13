package com.lc.server.business.di

import com.lc.server.business.auth.AuthService
import com.lc.server.business.auth.AuthServiceImpl
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.business.jwtconfig.JwtConfigImpl
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
private val businessModule = module {

    // jwt
    single<JwtConfig> { JwtConfigImpl() }

    // service
    single<AuthService> { AuthServiceImpl(get(), get()) }

}

@KtorExperimentalLocationsAPI
val getBusinessModule = businessModule
