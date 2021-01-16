package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/remove-chat-group-detail")
data class RemoveChatGroupDetailRequest(
    val chatGroupId: Int? = null,
    val friendUserId: String? = null,
)
