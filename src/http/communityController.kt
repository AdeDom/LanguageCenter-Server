package com.lc.server.http

import com.lc.server.business.community.CommunityService
import com.lc.server.models.request.FetchCommunityRequest
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.communityController(service: CommunityService) {

    get<FetchCommunityRequest> {
        val response = service.fetchCommunity(call.userId)
        call.respond(response)
    }

}
