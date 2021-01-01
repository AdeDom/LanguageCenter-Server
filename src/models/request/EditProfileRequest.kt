package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/account/edit-profile")
data class EditProfileRequest(
    val givenName: String? = null,
    val familyName: String? = null,
    val gender: String? = null,
    val birthDate: Long? = null,
    val aboutMe: String? = null,
)
