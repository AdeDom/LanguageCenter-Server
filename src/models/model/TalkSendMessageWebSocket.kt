package com.lc.server.models.model

data class TalkSendMessageWebSocket(
    val talkId: String = "",
    val fromUserId: String = "",
    val toUserId: String,
    val messages: String,
    val dateString: String = "",
    val timeString: String = "",
    val dateTimeLong: Long = 0,
    val isRead: Boolean = false,
)
