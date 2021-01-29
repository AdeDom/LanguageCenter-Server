package com.lc.server.models.model

data class Vocabulary(
    val vocabularyId: Int,
    val vocabulary: String,
    val sourceLanguage: String,
    val vocabularyGroupName: String,
    val translations: List<Translation> = emptyList(),
)
