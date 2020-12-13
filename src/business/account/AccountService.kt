package com.lc.server.business.account

import com.lc.server.models.response.UserInfoResponse

interface AccountService {

    fun fetchUserInfo(userId: String?): UserInfoResponse

}
