package com.lc.server.models.response

import com.lc.server.models.model.ChatListUserInfo

data class ChatListUserInfoResponse(
    var success: Boolean = false,
    var message: String? = null,
    var chatListUserInfo: ChatListUserInfo? = null,
)
