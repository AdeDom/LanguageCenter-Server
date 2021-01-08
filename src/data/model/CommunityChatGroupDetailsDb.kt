package com.lc.server.data.model

data class CommunityChatGroupDetailsDb(
    val chatGroupDetailId: Int = 0,
    val chatGroupId: Int = 0,
    val userId: String = "",
    val created: Long = 0,
    val updated: Long? = null,
)
