package com.lc.server.data.model

import com.lc.server.models.model.UserInfoLocale

data class UserInfoDb(
    val userId: String? = null,
    val email: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val gender: String? = null,
    val birthDate: Long? = null,
    val verifiedEmail: Boolean? = false,
    val aboutMe: String? = null,
    val created: Long? = null,
    val updated: Long? = null,
    val localNatives: List<UserInfoLocale> = emptyList(),
    val localLearnings: List<UserInfoLocale> = emptyList(),
)
