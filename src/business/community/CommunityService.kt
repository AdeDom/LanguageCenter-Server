package com.lc.server.business.community

import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchCommunityResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface CommunityService {

    fun fetchCommunity(userId: String?): FetchCommunityResponse

    fun addAlgorithm(userId: String?, addAlgorithmRequest: AddAlgorithmRequest): BaseResponse

}
