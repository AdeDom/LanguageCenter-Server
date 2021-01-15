package com.lc.server.models.response

import com.lc.server.models.model.AddChatGroupDetail

data class FetchAddChatGroupDetailResponse(
    var success: Boolean = false,
    var message: String? = null,
    var addChatGroupDetailList: List<AddChatGroupDetail> = emptyList(),
)
