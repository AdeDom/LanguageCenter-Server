package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/send-message")
data class SendMessageRequest(
    val talkId: String? = null,
    val toUserId: String? = null,
    val messages: String? = null,
)
