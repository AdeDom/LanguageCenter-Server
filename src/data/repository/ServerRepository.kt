package com.lc.server.data.repository

import com.lc.server.data.model.UserInfoDb
import com.lc.server.data.model.UserLocaleDb
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.GuideUpdateProfileRequest
import io.ktor.locations.*
import models.response.GoogleApiUserInfoResponse

@KtorExperimentalLocationsAPI
internal interface ServerRepository {

    fun signIn(googleApiUserInfo: GoogleApiUserInfoResponse): Boolean

    fun isUpdateProfile(id: String): Boolean

    fun fetchUserInfo(userId: String): UserInfoDb

    fun guideUpdateProfile(userId: String, guideUpdateProfileRequest: GuideUpdateProfileRequest): Boolean

    fun editProfile(userId: String, editProfileRequest: EditProfileRequest): Boolean

    fun editLocaleNative(userId: String, locales: List<UserInfoLocale>): Boolean

    fun editLocaleLearning(userId: String, locales: List<UserInfoLocale>): Boolean

    fun getUserInfoCommunity(userId: String): List<UserInfoDb>

    fun getUserLocaleCommunity(userId: String): List<UserLocaleDb>

}
