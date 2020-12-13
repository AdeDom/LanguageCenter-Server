package com.lc.server.models.response

import com.lc.server.models.model.UserInfo

data class UserInfoResponse(
    var success: Boolean = false,
    var message: String? = null,
    var userInfo: UserInfo? = null,
)
