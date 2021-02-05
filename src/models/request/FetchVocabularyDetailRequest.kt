package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/vocabulary/fetch-vocabulary-detail/{vocabularyGroup}")
data class FetchVocabularyDetailRequest(
    val vocabularyGroup: String? = null,
    val vocabularyGroupId: Int? = null,
)
