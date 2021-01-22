package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/{chatListUserInfo}")
data class ChatListUserInfoRequest(
    val chatListUserInfo: String? = null,
    val otherUserId: String? = null,
)
