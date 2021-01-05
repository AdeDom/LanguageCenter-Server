package com.lc.server.business.di

import com.lc.server.business.account.AccountService
import com.lc.server.business.account.AccountServiceImpl
import com.lc.server.business.auth.AuthService
import com.lc.server.business.auth.AuthServiceImpl
import com.lc.server.business.business.ServerBusiness
import com.lc.server.business.business.ServerBusinessImpl
import com.lc.server.business.community.CommunityService
import com.lc.server.business.community.CommunityServiceImpl
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
    single<CommunityService> { CommunityServiceImpl(get(), get()) }
    single<AccountService> { AccountServiceImpl(get(), get()) }

}

@KtorExperimentalLocationsAPI
val getBusinessModule = businessModule
