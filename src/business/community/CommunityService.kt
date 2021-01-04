package com.lc.server.business.community

import com.lc.server.models.response.FetchCommunityResponse

interface CommunityService {

    fun fetchCommunity(userId: String?): FetchCommunityResponse

}
