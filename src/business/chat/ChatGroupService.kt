package com.lc.server.business.chat

import com.lc.server.models.request.*
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchAddChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface ChatGroupService {

    fun addChatGroupNew(userId: String?, addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse

    fun addChatGroup(userId: String?, addChatGroupRequest: AddChatGroupRequest): BaseResponse

    fun fetchChatGroup(userId: String?): FetchChatGroupResponse

    fun fetchChatGroupDetail(chatGroupId: String?): FetchChatGroupDetailResponse

    fun renameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): BaseResponse

    fun removeChatGroup(chatGroupId: String?): BaseResponse

    fun fetchAddChatGroupDetail(userId: String?): FetchAddChatGroupDetailResponse

    fun addChatGroupDetail(addChatGroupDetailRequest: AddChatGroupDetailRequest): BaseResponse

    fun changeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse

    fun removeChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse

}
