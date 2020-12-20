package com.lc.server.business.account

import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.UserInfoResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface AccountService {

    fun fetchUserInfo(userId: String?): UserInfoResponse

    fun editProfile(userId: String?, editProfileRequest: EditProfileRequest): BaseResponse

}
