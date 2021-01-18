package com.lc.server.http

import com.lc.server.business.chats.ChatsService
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.chatsController(service: ChatsService) {

    post<SendMessageRequest> {
        val request = call.receive<SendMessageRequest>()
        val response = service.sendMessage(call.userId, request)
        call.respond(response)
    }

}
