package com.lc.server.data.model

data class CommunityUserLocalesDb(
    val localeId: Int = 0,
    val userId: String = "",
    val locale: String? = null,
    val level: Int = 0,
    val localeType: String = "",
    val created: Long = 0,
)
