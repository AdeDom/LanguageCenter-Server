package com.lc.server.http

import com.lc.server.business.chat.ChatGroupService
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.request.FetchChatGroupRequest
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.chatGroupController(service: ChatGroupService) {

    post<AddChatGroupNewRequest> {
        val request = call.receive<AddChatGroupNewRequest>()
        val response = service.addChatGroupNew(call.userId, request)
        call.respond(response)
    }

    post<AddChatGroupRequest> {
        val request = call.receive<AddChatGroupRequest>()
        val response = service.addChatGroup(call.userId, request)
        call.respond(response)
    }

    get<FetchChatGroupRequest> {
        val response = service.fetchChatGroup(call.userId)
        call.respond(response)
    }

}
