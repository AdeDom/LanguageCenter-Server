package com.lc.server.data.model

data class CommunityChatGroupsDb(
    val chatGroupId: Int = 0,
    val groupName: String = "",
    val userId: String = "",
    val created: Long = 0,
    val updated: Long? = null,
)
