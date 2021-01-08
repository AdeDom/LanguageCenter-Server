package com.lc.server.data.model

data class CommunityUsersDb(
    val userId: String = "",
    val email: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val gender: String? = null,
    val birthDate: Long? = null,
    val verifiedEmail: Boolean? = null,
    val aboutMe: String? = null,
    val isUpdateProfile: Boolean = false,
    val created: Long = 0,
    val updated: Long? = null,
)
