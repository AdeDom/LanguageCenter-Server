package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/friend-group/change-chat-group")
data class ChangeChatGroupRequest(
    val chatGroupId: Int? = null,
    val friendUserId: String? = null,
    val changeChatGroupId: Int? = null,
)
