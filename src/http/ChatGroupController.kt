package com.lc.server.http

import com.lc.server.business.chat.ChatGroupService
import com.lc.server.models.request.*
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

    get<FetchChatGroupDetailRequest> { request ->
        val response = service.fetchChatGroupDetail(request)
        call.respond(response)
    }

    put<RenameChatGroupRequest> {
        val request = call.receive<RenameChatGroupRequest>()
        val response = service.renameChatGroup(request)
        call.respond(response)
    }

    delete<RemoveChatGroupRequest> { request ->
        val response = service.removeChatGroup(request.chatGroupId)
        call.respond(response)
    }

}
