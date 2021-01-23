package com.lc.server.models.model

data class Talk(
    val talkId: String,
    val fromUserId: String,
    val toUserId: String,
    val messages: String,
    val isSendMessage: Boolean,
    val isReceiveMessage: Boolean,
    val isRead: Boolean,
    val isShow: Boolean,
    val dateTime: Long,
    val dateTimeUpdated: Long?,
)
