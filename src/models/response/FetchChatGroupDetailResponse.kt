package com.lc.server.models.response

import com.lc.server.models.model.ChatGroupDetail

data class FetchChatGroupDetailResponse(
    var success: Boolean = false,
    var message: String? = null,
    var chatGroupDetails: List<ChatGroupDetail> = emptyList(),
)
