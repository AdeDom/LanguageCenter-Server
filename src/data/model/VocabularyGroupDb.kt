package com.lc.server.data.model

data class VocabularyGroupDb(
    val vocabularyGroupId: Int,
    val vocabularyGroupName: String,
    val created: Long,
    val updated: Long?,
    val isShow: Boolean,
)
