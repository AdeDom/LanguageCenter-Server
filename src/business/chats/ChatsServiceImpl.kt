package com.lc.server.business.chats

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.response.BaseResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class ChatsServiceImpl(
    private val repository: ServerRepository,
) : ChatsService {

    override fun sendMessage(userId: String?, sendMessageRequest: SendMessageRequest): BaseResponse {
        val response = BaseResponse()
        val (talkId, toUserId, messages) = sendMessageRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"
            talkId.isNullOrBlank() -> "isNullOrBlank"
            toUserId.isNullOrBlank() -> "isNullOrBlank"
            messages.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.sendMessage(userId, sendMessageRequest)
                "Send message success"
            }
        }

        response.message = message
        return response
    }

    override fun sendMessage(talkSendMessageWebSocket: TalkSendMessageWebSocket): Boolean {
        return repository.sendMessage(talkSendMessageWebSocket)
    }

}
