package com.lc.server.data.di

import com.lc.server.data.repository.ServerRepository
import com.lc.server.data.repository.ServerRepositoryImpl
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
private val dataModule = module {

    single<ServerRepository> { ServerRepositoryImpl() }

}

@KtorExperimentalLocationsAPI
val getDataModule = dataModule
