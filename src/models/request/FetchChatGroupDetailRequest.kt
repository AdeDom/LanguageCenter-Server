package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chat-group/fetch-chat-group-detail/{chatGroupId}")
data class FetchChatGroupDetailRequest(
    val chatGroupId: String? = null,
)
