package com.lc.server.business.chat

import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchChatGroupResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface ChatGroupService {

    fun addChatGroupNew(userId: String?, addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse

    fun addChatGroup(userId: String?, addChatGroupRequest: AddChatGroupRequest): BaseResponse

    fun fetchChatGroup(userId: String?): FetchChatGroupResponse

}
