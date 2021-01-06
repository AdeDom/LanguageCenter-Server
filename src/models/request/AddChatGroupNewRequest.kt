package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat/add-chat-group-new")
data class AddChatGroupNewRequest(
    val userId: String? = null,
)
