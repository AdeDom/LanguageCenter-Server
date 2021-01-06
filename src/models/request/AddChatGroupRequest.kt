package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/add-chat-group")
data class AddChatGroupRequest(
    val groupName: String? = null,
)
