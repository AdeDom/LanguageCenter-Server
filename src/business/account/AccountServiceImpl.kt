package com.lc.server.business.account

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.UserInfo
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.UserInfoResponse
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
                    birthDate = business.convertDateTimeLongToString(db.birthDate),
                    verifiedEmail = db.verifiedEmail,
                    aboutMe = db.aboutMe,
                    created = business.convertDateTimeLongToString(db.created),
                    updated = business.convertDateTimeLongToString(db.updated),
                    locales = db.locales,
                )

                response.success = true
                response.userInfo = userInfo
                "Fetch user info success"
            }
        }

        response.message = message
        return response
    }

    override fun editProfile(userId: String?, editProfileRequest: EditProfileRequest): BaseResponse {
        val response = BaseResponse()
        val (email, givenName, familyName, gender, birthDate, aboutMe) = editProfileRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "Null"
            email.isNullOrBlank() -> "Null"
            givenName.isNullOrBlank() -> "Null"
            gender.isNullOrBlank() -> "Null"
            birthDate == null -> "Null"

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

}
