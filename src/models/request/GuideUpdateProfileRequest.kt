package com.lc.server.models.request

import com.lc.server.models.model.UserInfoLocale
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/account/guide-update-profile")
data class GuideUpdateProfileRequest(
    val localNatives: List<UserInfoLocale> = emptyList(),
    val localLearnings: List<UserInfoLocale> = emptyList(),
    val gender: String? = null,
    val birthDate: Long? = null,
)
