package com.lc.server

import com.lc.server.business.account.AccountService
import com.lc.server.business.auth.AuthService
import com.lc.server.business.chats.ChatsService
import com.lc.server.business.community.CommunityService
import com.lc.server.business.di.getBusinessModule
import com.lc.server.business.friendgroup.FriendGroupService
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.business.jwtconfig.UserPrincipal
import com.lc.server.business.vocabulary.VocabularyService
import com.lc.server.data.di.getDataModule
import com.lc.server.http.*
import com.lc.server.util.DatabaseConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.cio.websocket.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger
import java.time.Duration

/**
 * AdeDom Jaiyen
 * https://github.com/AdeDom
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@InternalAPI
@KtorExperimentalLocationsAPI
fun Application.module() {
    // database mysql
    val databaseConfig = DatabaseConfig.Heroku
    val config = HikariConfig().apply {
        jdbcUrl = databaseConfig.jdbcUrl
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = databaseConfig.username
        password = databaseConfig.password
        maximumPoolSize = 10
    }
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    // start project
    install(DefaultHeaders)
    install(CallLogging)

    // route location
    install(Locations)

    // gson convertor json
    install(ContentNegotiation) {
        gson {
        }
    }

    // web socket
    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(60)
    }

    // koin dependencies injection
    install(Koin) {
        SLF4JLogger()
        modules(getBusinessModule, getDataModule)
    }
    val authService: AuthService by inject()
    val jwtConfig: JwtConfig by inject()
    val communityService: CommunityService by inject()
    val chatsService: ChatsService by inject()
    val friendGroupService: FriendGroupService by inject()
    val vocabularyService: VocabularyService by inject()
    val accountService: AccountService by inject()

    // jwt
    install(Authentication) {
        jwt {
            verifier(jwtConfig.verifier)
            realm = jwtConfig.realm
            validate {
                val userId = it.payload.getClaim(jwtConfig.userId).asString()
                if (userId != null) {
                    UserPrincipal(userId)
                } else {
                    null
                }
            }
        }
    }

    // route
    install(Routing) {
        authController(authService)

        authenticate {
            communityController(communityService)
            chatsController(chatsService)
            friendGroupController(friendGroupService)
            vocabularyController(vocabularyService)
            accountController(accountService)
        }

        chatsWebSocket(jwtConfig)

        mockDataController()
    }
}

internal fun getHttpClientApache() = HttpClient(Apache) {
    install(HttpTimeout) {
        requestTimeoutMillis = 60_000
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
}
