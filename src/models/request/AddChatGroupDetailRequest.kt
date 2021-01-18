package com.lc.server.models.request

data class AddChatGroupDetailRequest(
    val chatGroupId: Int? = null,
    val userId: String? = null,
)
