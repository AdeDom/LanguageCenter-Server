package com.lc.server.business.chat

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchChatGroupResponse
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

    override fun addChatGroup(userId: String?, addChatGroupRequest: AddChatGroupRequest): BaseResponse {
        val response = BaseResponse()
        val (groupName) = addChatGroupRequest

        val message: String = when {
            userId.isNullOrBlank() -> "Null"
            groupName.isNullOrBlank() -> "Null"

            else -> {
                response.success = repository.addChatGroup(userId, addChatGroupRequest)
                "Add chat group success"
            }
        }

        response.message = message
        return response
    }

    override fun fetchChatGroup(userId: String?): FetchChatGroupResponse {
        val response = FetchChatGroupResponse()

        val message: String = when {
            userId.isNullOrBlank() -> "Null"

            else -> {
                response.chatGroup = repository.fetchChatGroup(userId)
                response.success = true
                "Fetch chat group success"
            }
        }

        response.message = message
        return response
    }

}
