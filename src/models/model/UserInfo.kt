package com.lc.server.models.model

data class UserInfo(
    val userId: String? = null,
    val email: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val gender: String? = null,
    val birthDate: String? = null,
    val verifiedEmail: Boolean? = null,
    val aboutMe: String? = null,
    val created: String? = null,
    val updated: String? = null,
    val locales: List<UserInfoLocale> = emptyList(),
)