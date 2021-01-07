package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/rename-chat-group")
data class RenameChatGroupRequest(
    val chatGroupId: Int? = null,
    val groupName: String? = null,
)
