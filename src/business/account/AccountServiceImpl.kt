package com.lc.server.business.account

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.UserInfo
import com.lc.server.models.request.EditLocaleRequest
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.UserInfoResponse
import com.lc.server.util.LanguageCenterConstant
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class AccountServiceImpl(
    private val repository: ServerRepository,
    private val business: ServerBusiness,
) : AccountService {

    override fun fetchUserInfo(userId: String?): UserInfoResponse {
        val response = UserInfoResponse()

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val db = repository.fetchUserInfo(userId)
                val userInfo = UserInfo(
                    userId = db.userId,
                    email = db.email,
                    givenName = db.givenName?.capitalize(),
                    familyName = db.familyName?.capitalize(),
                    name = db.name?.capitalize(),
                    picture = db.picture,
                    gender = db.gender,
                    birthDateString = business.convertDateTimeLongToString(db.birthDate),
                    birthDateLong = db.birthDate,
                    verifiedEmail = db.verifiedEmail,
                    aboutMe = db.aboutMe,
                    created = business.convertDateTimeLongToString(db.created),
                    updated = business.convertDateTimeLongToString(db.updated),
                    localNatives = db.localNatives,
                    localLearnings = db.localLearnings,
                )

                response.success = true
                response.userInfo = userInfo
                "Fetch user info success"
            }
        }

        response.message = message
        return response
    }

    override fun guideUpdateProfile(
        userId: String?,
        guideUpdateProfileRequest: GuideUpdateProfileRequest
    ): BaseResponse {
        val response = BaseResponse()

        val message: String = when {
            userId.isNullOrBlank() -> "Null"

            else -> {
                response.success = repository.guideUpdateProfile(userId, guideUpdateProfileRequest)
                "Guide update profile success"
            }
        }

        response.message = message
        return response
    }

    override fun editProfile(userId: String?, editProfileRequest: EditProfileRequest): BaseResponse {
        val response = BaseResponse()
        val (givenName, familyName, gender, birthDate, aboutMe) = editProfileRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "Null"
            givenName.isNullOrBlank() -> "Null"
            familyName == null -> "Null"
            gender.isNullOrBlank() -> "Null"
            birthDate == null -> "Null"
            aboutMe == null -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.editProfile(userId, editProfileRequest)
                "Edit profile success"
            }
        }

        response.message = message
        return response
    }

    override fun editLocale(userId: String?, editLocaleRequest: EditLocaleRequest): BaseResponse {
        val response = BaseResponse()
        val (editLocaleType, locales) = editLocaleRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "Null"
            editLocaleType.isNullOrBlank() -> "Null"
            locales.isNullOrEmpty() -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = when (editLocaleType) {
                    LanguageCenterConstant.LOCALE_NATIVE -> repository.editLocaleNative(userId, locales)
                    LanguageCenterConstant.LOCALE_LEARNING -> repository.editLocaleLearning(userId, locales)
                    else -> false
                }
                "Edit locale success"
            }
        }

        response.message = message
        return response
    }

}
