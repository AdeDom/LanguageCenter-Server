package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/chats/languageCenter/{translate}")
data class LanguageCenterTranslateRequest(
    val translate: String? = null,
    val vocabulary: String? = null,
)
