package com.lc.server.models.model

data class AddChatGroupDetail(
    val userId: String? = null,
    val email: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val birthDateString: String? = null,
    val birthDateLong: Long? = null,
    val aboutMe: String? = null,
    val created: Long? = null,
    val localNatives: List<UserInfoLocale> = emptyList(),
    val localLearnings: List<UserInfoLocale> = emptyList(),
)
