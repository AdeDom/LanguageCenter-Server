package com.lc.server.business.chats

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.response.SendMessageResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class ChatsServiceImpl(
    private val repository: ServerRepository,
) : ChatsService {

    override fun sendMessage(userId: String?, sendMessageRequest: SendMessageRequest): SendMessageResponse {
        val response = SendMessageResponse()
        val (talkId, toUserId, messages) = sendMessageRequest

        val dateTimeLong = System.currentTimeMillis()
        response.dateTimeLong = dateTimeLong

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"
            talkId.isNullOrBlank() -> "isNullOrBlank"
            toUserId.isNullOrBlank() -> "isNullOrBlank"
            messages.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database
            repository.isValidateSendMessage(talkId) -> {
                response.success = true
                "Send message again success"
            }

            // execute
            else -> {
                response.success = repository.sendMessage(userId, sendMessageRequest, dateTimeLong)
                "Send message success"
            }
        }

        response.message = message
        return response
    }

}
