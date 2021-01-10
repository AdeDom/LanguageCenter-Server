package com.lc.server.business.community

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchCommunityResponse
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
                val getCommunityUsers = repository.getCommunityUsers(userId)
                val getCommunityUserLocales = repository.getCommunityUserLocales()
                val getCommunityAlgorithms = repository.getCommunityAlgorithms(userId)
                val getCommunityFriend = repository.getCommunityFriend(userId)
                val getCommunityMyBirthDate = repository.getCommunityMyBirthDate(userId)

                val filterCommunityUsersNew = business.filterCommunityUsersNew(
                    getCommunityUsers,
                    getCommunityFriend,
                )

                val ratioAlgorithm = business.findRatioAlgorithm(getCommunityAlgorithms)

                val getAlgorithmA = business.getAlgorithmA(
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                )

                val getAlgorithmB = business.getAlgorithmB(
                    userId,
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                    getCommunityUserLocales,
                )

                val getAlgorithmC = business.getAlgorithmC(
                    userId,
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                    getCommunityUserLocales,
                )

                val getAlgorithmD1 = business.getAlgorithmD1(
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                )

                val getAlgorithmD2 = business.getAlgorithmD2(
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                )

                val getAlgorithmE1 = business.getAlgorithmE1(
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                    getCommunityMyBirthDate,
                )

                val getAlgorithmE2 = business.getAlgorithmE2(
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                    getCommunityMyBirthDate,
                )

                val getAlgorithmF = business.getAlgorithmF(
                    userId,
                    ratioAlgorithm,
                    filterCommunityUsersNew,
                    getCommunityUserLocales,
                )

                val randomCommunities = business.randomCommunities(
                    getAlgorithmA +
                            getAlgorithmB +
                            getAlgorithmC +
                            getAlgorithmD1 +
                            getAlgorithmD2 +
                            getAlgorithmE1 +
                            getAlgorithmE2 +
                            getAlgorithmF
                )

                val communities = business.mapToCommunities(randomCommunities, getCommunityUserLocales)

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
