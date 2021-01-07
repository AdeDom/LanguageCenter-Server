package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/remove-chat-group/{chatGroupId}")
data class RemoveChatGroupRequest(
    val chatGroupId: String? = null,
)
