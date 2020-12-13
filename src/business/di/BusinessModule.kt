package com.lc.server.business.di

import com.lc.server.business.auth.AuthService
import com.lc.server.business.auth.AuthServiceImpl
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
private val businessModule = module {

    // service
    single<AuthService> { AuthServiceImpl() }

}

@KtorExperimentalLocationsAPI
val getBusinessModule = businessModule
