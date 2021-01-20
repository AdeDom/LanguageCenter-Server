package com.lc.server.business.chats

import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.response.SendMessageResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface ChatsService {

    fun sendMessage(userId: String?, sendMessageRequest: SendMessageRequest): SendMessageResponse

}
