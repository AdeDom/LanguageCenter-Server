package com.lc.server.models.model

data class Vocabulary(
    val vocabularyId: String,
    val userInfo: Community? = null,
    val vocabulary: String,
    val sourceLanguage: String,
    val reference: String? = null,
    val created: Long,
    val vocabularyGroupName: String,
    val translations: List<Translation> = emptyList(),
)
