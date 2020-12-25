package com.lc.server.data.repository

import com.lc.server.data.model.UserInfoDb
import com.lc.server.models.request.EditProfileRequest
import io.ktor.locations.*
import models.response.GoogleApiUserInfoResponse

@KtorExperimentalLocationsAPI
internal interface ServerRepository {

    fun signIn(googleApiUserInfo: GoogleApiUserInfoResponse): Boolean

    fun isUpdateProfile(id: String): Boolean

    fun fetchUserInfo(userId: String): UserInfoDb

    fun editProfile(userId: String, editProfileRequest: EditProfileRequest): Boolean

}
