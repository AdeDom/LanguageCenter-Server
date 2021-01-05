package com.lc.server.data.model

data class UserLocaleDb(
    val userId: String? = null,
    val locale: String? = null,
    val level: Int = 0,
    val localeType: String? = null,
)
