package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/update-receive-messages")
data class UpdateReceiveMessageRequest(
    val talkIdList: List<String> = emptyList()
)
