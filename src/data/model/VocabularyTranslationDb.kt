package com.lc.server.data.model

data class VocabularyTranslationDb(
    val vocabularyId: Int,
    val vocabulary: String,
    val sourceLanguage: String,
    val vocabularyGroupName: String,
    val translationId: Int,
    val translation: String,
    val targetLanguage: String,
)