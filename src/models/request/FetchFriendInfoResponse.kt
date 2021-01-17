package com.lc.server.models.request

import com.lc.server.models.model.Community

data class FetchFriendInfoResponse(
    var success: Boolean = false,
    var message: String? = null,
    var friendInfoList: List<Community> = emptyList(),
)
