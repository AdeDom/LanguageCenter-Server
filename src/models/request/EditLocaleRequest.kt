package com.lc.server.models.request

import com.lc.server.models.model.UserInfoLocale
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/account/edit-locale")
data class EditLocaleRequest(
    val editLocaleType: String? = null,
    val locales: List<UserInfoLocale> = emptyList(),
)
