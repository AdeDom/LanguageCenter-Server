package com.lc.server.data.model

data class VocabularyTranslationDb(
    val vocabularyId: String,
    val userId: String,
    val vocabulary: String,
    val sourceLanguage: String,
    val reference: String,
    val created: Long,
    val vocabularyGroupName: String,
    val translationId: Int,
    val translationIdToVocabularyId: String,
    val translation: String?,
    val targetLanguage: String,
)
