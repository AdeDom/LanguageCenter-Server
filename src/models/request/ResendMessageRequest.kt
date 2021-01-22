package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/resend-message")
data class ResendMessageRequest(
    val talkId: String? = null,
    val fromUserId: String? = null,
    val toUserId: String? = null,
    val messages: String? = null,
    val dateTimeLong: Long? = 0,
)
