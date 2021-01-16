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
        val response = service.fetchChatGroupDetail(request.chatGroupId)
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

    get<FetchAddChatGroupDetailRequest> {
        val response = service.fetchAddChatGroupDetail(call.userId)
        call.respond(response)
    }

    post<AddChatGroupDetailRequest> {
        val request = call.receive<AddChatGroupDetailRequest>()
        val response = service.addChatGroupDetail(request)
        call.respond(response)
    }

    put<ChangeChatGroupRequest> {
        val request = call.receive<ChangeChatGroupRequest>()
        val response = service.changeChatGroup(request)
        call.respond(response)
    }

    delete<RemoveChatGroupDetailRequest> {
        val request = call.receive<RemoveChatGroupDetailRequest>()
        val response = service.removeChatGroupDetail(request)
        call.respond(response)
    }

}
