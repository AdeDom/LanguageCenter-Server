package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/friend-group/{fetchChatGroupDetail}")
data class FetchChatGroupDetailRequest(
    val fetchChatGroupDetail: String? = null,
    val chatGroupId: String? = null,
)
