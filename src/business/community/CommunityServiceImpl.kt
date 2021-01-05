package com.lc.server.business.community

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.UserInfo
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.response.FetchCommunityResponse
import com.lc.server.util.LanguageCenterConstant
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class CommunityServiceImpl(
    private val repository: ServerRepository,
    private val business: ServerBusiness,
) : CommunityService {

    override fun fetchCommunity(userId: String?): FetchCommunityResponse {
        val response = FetchCommunityResponse()

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val userInfoCommunity = repository.getUserInfoCommunity(userId).map { userInfo ->
                    UserInfo(
                        userId = userInfo.userId,
                        email = userInfo.email,
                        givenName = userInfo.givenName?.capitalize(),
                        familyName = userInfo.familyName?.capitalize(),
                        name = userInfo.name?.capitalize(),
                        picture = userInfo.picture,
                        gender = userInfo.gender,
                        birthDateString = business.convertDateTimeLongToString(userInfo.birthDate),
                        birthDateLong = userInfo.birthDate,
                        verifiedEmail = userInfo.verifiedEmail,
                        aboutMe = userInfo.aboutMe,
                        created = business.convertDateTimeLongToString(userInfo.created),
                        updated = business.convertDateTimeLongToString(userInfo.updated),
                    )
                }
                val userLocaleCommunity = repository.getUserLocaleCommunity(userId)

                val userInfoList = mutableListOf<UserInfo>()
                userInfoCommunity.forEach { userInfo ->
                    val userLocaleNativeList = mutableListOf<UserInfoLocale>()
                    val userLocaleLearningList = mutableListOf<UserInfoLocale>()

                    userLocaleCommunity.filter { userLocale ->
                        userLocale.userId == userInfo.userId
                    }.filter { userLocale ->
                        userLocale.localeType == LanguageCenterConstant.LOCALE_NATIVE
                    }.forEach { userLocale ->
                        userLocaleNativeList.add(
                            UserInfoLocale(
                                locale = userLocale.locale,
                                level = userLocale.level
                            )
                        )
                    }

                    userLocaleCommunity.filter { userLocale ->
                        userLocale.userId == userInfo.userId
                    }.filter { userLocale ->
                        userLocale.localeType == LanguageCenterConstant.LOCALE_LEARNING
                    }.forEach { userLocale ->
                        userLocaleLearningList.add(
                            UserInfoLocale(
                                locale = userLocale.locale,
                                level = userLocale.level
                            )
                        )
                    }

                    userInfoList.add(
                        userInfo.copy(
                            localNatives = userLocaleNativeList,
                            localLearnings = userLocaleLearningList,
                        )
                    )
                }

                response.userInfoList = userInfoList
                response.success = true
                "Fetch user info list success"
            }
        }

        response.message = message
        return response
    }

}
