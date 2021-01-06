package com.lc.server.models.response

import com.lc.server.models.model.ChatGroup

data class FetchChatGroupResponse(
    var success: Boolean = false,
    var message: String? = null,
    var chatGroup: List<ChatGroup> = emptyList(),
)
