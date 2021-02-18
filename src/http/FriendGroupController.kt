package com.lc.server.http

import com.lc.server.business.friendgroup.FriendGroupService
import com.lc.server.models.request.*
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
internal fun Route.friendGroupController(service: FriendGroupService) {

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

    get<FetchAddChatGroupDetailRequest> {
        val response = service.fetchAddChatGroupDetail(call.userId)
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

    post<AddChatGroupFriendRequest> {
        val request = call.receive<AddChatGroupFriendRequest>()
        val response = service.addChatGroupFriend(call.userId, request)
        call.respond(response)
    }

}
