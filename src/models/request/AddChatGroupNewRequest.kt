package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/add-chat-group-new")
data class AddChatGroupNewRequest(
    val userId: String? = null,
)
