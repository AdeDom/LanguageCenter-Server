package com.lc.server.models.request

data class TranslationFeedbackRequest(
    val translationId: Int? = null,
    val isCorrect: Boolean? = null,
)
