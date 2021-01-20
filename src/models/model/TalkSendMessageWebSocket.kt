package com.lc.server.models.model

data class TalkSendMessageWebSocket(
    val talkId: String = "",
    val fromUserId: String = "",
    val toUserId: String,
    val messages: String,
    val isRead: Boolean = false,
    val isShow: Boolean = true,
    val dateTimeLong: Long = 0,
    val dateTimeUpdated: Long? = null,
)
