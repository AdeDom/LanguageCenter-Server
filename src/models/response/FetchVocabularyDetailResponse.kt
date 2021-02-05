package com.lc.server.models.response

import com.lc.server.models.model.Vocabulary

data class FetchVocabularyDetailResponse(
    var success: Boolean = false,
    var message: String? = null,
    var vocabularies: List<Vocabulary> = emptyList(),
)
