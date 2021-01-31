package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/vocabulary/add-vocabulary-translation")
data class AddVocabularyTranslationRequest(
    val vocabulary: String? = null,
    val source: String? = null,
    val target: String? = null,
    val translations: List<String> = emptyList(),
)
