package com.lc.server.models.model

data class TalkSendMessageWebSocket(
    val talkId: String = "",
    val fromUserId: String = "",
    val toUserId: String?,
    val messages: String?,
    val dateTime: Long = 0,
    val isRead: Boolean = false,
)
