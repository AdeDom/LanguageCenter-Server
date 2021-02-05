package com.lc.server.models.model

data class VocabularyGroup(
    val vocabularyGroupId: Int,
    val vocabularyGroupName: String,
    val created: Long,
    val updated: Long?,
    val isShow: Boolean,
)
