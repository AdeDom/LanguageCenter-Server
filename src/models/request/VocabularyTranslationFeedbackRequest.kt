package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/vocabulary/vocabulary-translation-feedback")
data class VocabularyTranslationFeedbackRequest(
    val vocabularyId: String? = null,
    val rating: Float? = null,
    val translations: List<TranslationFeedbackRequest> = emptyList(),
)
