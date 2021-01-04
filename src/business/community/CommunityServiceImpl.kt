package com.lc.server.business.community

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.response.FetchCommunityResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class CommunityServiceImpl(
    private val repository: ServerRepository,
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
                response.success = true
                "Fetch user info list success"
            }
        }

        response.message = message
        return response
    }

}
