package com.lc.server.models.response

import com.lc.server.models.model.ChatGroup

data class FetchChatGroupResponse(
    var success: Boolean = false,
    var message: String? = null,
    var chatGroups: List<ChatGroup> = emptyList(),
)
