package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/receive-message/{talkId}")
data class ReceiveMessageRequest(
    val talkId: String? = null,
)
