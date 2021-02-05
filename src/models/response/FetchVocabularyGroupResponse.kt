package com.lc.server.models.response

import com.lc.server.models.model.VocabularyGroup

data class FetchVocabularyGroupResponse(
    var success: Boolean = false,
    var message: String? = null,
    var vocabularyGroups: List<VocabularyGroup> = emptyList(),
)
