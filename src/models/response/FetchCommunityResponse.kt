package com.lc.server.models.response

import com.lc.server.models.model.UserInfo

data class FetchCommunityResponse(
    var success: Boolean = false,
    var message: String? = null,
    var userInfoList: List<UserInfo> = emptyList(),
)
