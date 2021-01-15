package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/add-chat-group-detail")
data class AddChatGroupDetailRequest(
    val chatGroupId: Int? = null,
    val userId: String? = null,
)
