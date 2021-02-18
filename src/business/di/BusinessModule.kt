package com.lc.server.business.di

import com.lc.server.business.account.AccountService
import com.lc.server.business.account.AccountServiceImpl
import com.lc.server.business.auth.AuthService
import com.lc.server.business.auth.AuthServiceImpl
import com.lc.server.business.business.ServerBusiness
import com.lc.server.business.business.ServerBusinessImpl
import com.lc.server.business.chats.ChatsService
import com.lc.server.business.chats.ChatsServiceImpl
import com.lc.server.business.community.CommunityService
import com.lc.server.business.community.CommunityServiceImpl
import com.lc.server.business.friendgroup.FriendGroupService
import com.lc.server.business.friendgroup.FriendGroupServiceImpl
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.business.jwtconfig.JwtConfigImpl
import com.lc.server.business.vocabulary.VocabularyService
import com.lc.server.business.vocabulary.VocabularyServiceImpl
import io.ktor.locations.*
import io.ktor.util.*
import org.koin.dsl.module

@InternalAPI
@KtorExperimentalLocationsAPI
private val businessModule = module {

    // jwt
    single<JwtConfig> { JwtConfigImpl() }

    // logic
    single<ServerBusiness> { ServerBusinessImpl() }

    // service
    single<AuthService> { AuthServiceImpl(get(), get(), get()) }
    single<CommunityService> { CommunityServiceImpl(get(), get()) }
    single<ChatsService> { ChatsServiceImpl(get(), get()) }
    single<FriendGroupService> { FriendGroupServiceImpl(get(), get()) }
    single<VocabularyService> { VocabularyServiceImpl(get(), get()) }
    single<AccountService> { AccountServiceImpl(get(), get()) }

}

@InternalAPI
@KtorExperimentalLocationsAPI
val getBusinessModule = businessModule
