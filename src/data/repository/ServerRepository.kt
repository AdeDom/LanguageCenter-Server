package com.lc.server.data.repository

import com.lc.server.data.model.UserInfoDb
import models.response.GoogleApiUserInfoResponse

interface ServerRepository {

    fun signIn(googleApiUserInfo: GoogleApiUserInfoResponse): Boolean

    fun fetchUserInfo(userId: String): UserInfoDb

}
