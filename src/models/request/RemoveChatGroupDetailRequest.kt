package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/friend-group/remove-chat-group-detail")
data class RemoveChatGroupDetailRequest(
    val chatGroupId: Int? = null,
    val friendUserId: String? = null,
)
