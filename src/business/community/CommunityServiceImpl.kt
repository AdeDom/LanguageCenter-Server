package com.lc.server.business.community

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.Community
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.response.BaseResponse
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
                    Community(
                        userId = userInfo.userId,
                        email = userInfo.email,
                        givenName = userInfo.givenName?.capitalize(),
                        familyName = userInfo.familyName?.capitalize(),
                        name = userInfo.name?.capitalize(),
                        picture = userInfo.picture,
                        gender = userInfo.gender,
                        age = userInfo.birthDate?.let { business.getAgeInt(it) },
                        birthDateString = business.convertDateTimeLongToString(userInfo.birthDate),
                        birthDateLong = userInfo.birthDate,
                        verifiedEmail = userInfo.verifiedEmail,
                        aboutMe = userInfo.aboutMe,
                        created = business.convertDateTimeLongToString(userInfo.created),
                        updated = business.convertDateTimeLongToString(userInfo.updated),
                    )
                }
                val userLocaleCommunity = repository.getUserLocaleCommunity(userId)

                val communities = mutableListOf<Community>()
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

                    communities.add(
                        userInfo.copy(
                            algorithm = when ((1..6).random()) {
                                1 -> LanguageCenterConstant.ALGORITHM_A
                                2 -> LanguageCenterConstant.ALGORITHM_B
                                3 -> LanguageCenterConstant.ALGORITHM_C
                                4 -> LanguageCenterConstant.ALGORITHM_D
                                5 -> LanguageCenterConstant.ALGORITHM_E
                                6 -> LanguageCenterConstant.ALGORITHM_F
                                else -> null
                            },
                            localNatives = userLocaleNativeList,
                            localLearnings = userLocaleLearningList,
                        )
                    )
                }

                response.communities = communities
                response.success = true
                "Fetch user info list success"
            }
        }

        response.message = message
        return response
    }

    override fun addAlgorithm(userId: String?, addAlgorithmRequest: AddAlgorithmRequest): BaseResponse {
        val response = BaseResponse()
        val (algorithm) = addAlgorithmRequest

        val message: String = when {
            userId.isNullOrBlank() -> "Null"
            algorithm.isNullOrBlank() -> "Null"

            else -> {
                response.success = repository.addAlgorithm(userId, addAlgorithmRequest)
                "Add algorithm success"
            }
        }

        response.message = message
        return response
    }

}
