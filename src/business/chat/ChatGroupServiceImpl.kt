package com.lc.server.business.chat

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.response.BaseResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class ChatGroupServiceImpl(
    private val repository: ServerRepository,
) : ChatGroupService {

    override fun addChatGroupNew(userId: String?, addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse {
        val response = BaseResponse()

        val message: String = when {
            userId.isNullOrBlank() -> "Null"
            addChatGroupNewRequest.userId.isNullOrBlank() -> "Null"

            else -> {
                response.success = repository.addChatGroupNew(userId, addChatGroupNewRequest)
                "Add chat group new success"
            }
        }

        response.message = message
        return response
    }

}
