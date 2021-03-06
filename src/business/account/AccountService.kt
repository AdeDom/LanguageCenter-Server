package com.lc.server.business.account

import com.lc.server.models.request.EditLocaleRequest
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.FetchFriendInfoResponse
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.UserInfoResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface AccountService {

    fun fetchUserInfo(userId: String?): UserInfoResponse

    fun guideUpdateProfile(userId: String?, guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse

    fun editProfile(userId: String?, editProfileRequest: EditProfileRequest): BaseResponse

    fun editLocale(userId: String?, editLocaleRequest: EditLocaleRequest): BaseResponse

    fun fetchFriendInfo(userId: String?): FetchFriendInfoResponse

}
