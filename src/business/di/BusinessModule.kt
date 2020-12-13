package com.lc.server.business.di

import com.lc.server.business.auth.AuthService
import com.lc.server.business.auth.AuthServiceImpl
import com.lc.server.business.business.ServerBusiness
import com.lc.server.business.business.ServerBusinessImpl
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.business.jwtconfig.JwtConfigImpl
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
private val businessModule = module {

    // jwt
    single<JwtConfig> { JwtConfigImpl() }

    // logic
    single<ServerBusiness> { ServerBusinessImpl() }

    // service
    single<AuthService> { AuthServiceImpl(get(), get(), get()) }

}

@KtorExperimentalLocationsAPI
val getBusinessModule = businessModule
