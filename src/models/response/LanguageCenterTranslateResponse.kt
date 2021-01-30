package com.lc.server.models.response

import com.lc.server.models.model.Vocabulary

data class LanguageCenterTranslateResponse(
    var success: Boolean = false,
    var message: String? = null,
    var vocabulary: Vocabulary? = null,
)
