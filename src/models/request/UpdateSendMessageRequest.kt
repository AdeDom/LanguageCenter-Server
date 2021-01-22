package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/update-send-message/{talkId}")
data class UpdateSendMessageRequest(
    val talkId: String? = null,
)
