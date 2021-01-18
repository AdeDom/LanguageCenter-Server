package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/add-chat-group-friend")
data class AddChatGroupFriendRequest(
    val chatGroupId: Int? = null,
    val friendUserId: String? = null,
)
