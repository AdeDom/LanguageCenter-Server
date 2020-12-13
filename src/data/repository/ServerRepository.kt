package com.lc.server.data.repository

import models.response.GoogleApiUserInfoResponse

interface ServerRepository {

    fun signIn(googleApiUserInfo: GoogleApiUserInfoResponse): Boolean

}
