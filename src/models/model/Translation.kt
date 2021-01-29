package com.lc.server.models.model

data class Translation(
    val translationId: Int,
    val translation: String,
    val targetLanguage: String,
)
