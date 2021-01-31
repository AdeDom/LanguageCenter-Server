package com.lc.server.data.di

import com.lc.server.data.repository.ServerRepository
import com.lc.server.data.repository.ServerRepositoryImpl
import io.ktor.locations.*
import io.ktor.util.*
import org.koin.dsl.module

@InternalAPI
@KtorExperimentalLocationsAPI
private val dataModule = module {

    single<ServerRepository> { ServerRepositoryImpl() }

}

@InternalAPI
@KtorExperimentalLocationsAPI
val getDataModule = dataModule
