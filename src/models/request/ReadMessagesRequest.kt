package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/read-messages/{readUserId}")
data class ReadMessagesRequest(
    val readUserId: String? = null,
)
