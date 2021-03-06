package com.lc.server.business.friendgroup

import com.lc.server.models.request.*
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchAddChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal interface FriendGroupService {

    fun addChatGroup(userId: String?, addChatGroupRequest: AddChatGroupRequest): BaseResponse

    fun fetchChatGroup(userId: String?): FetchChatGroupResponse

    fun fetchChatGroupDetail(fetchChatGroupDetailRequest: FetchChatGroupDetailRequest): FetchChatGroupDetailResponse

    fun renameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): BaseResponse

    fun removeChatGroup(chatGroupId: String?): BaseResponse

    fun fetchAddChatGroupDetail(userId: String?): FetchAddChatGroupDetailResponse

    fun changeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse

    fun removeChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse

    fun addChatGroupFriend(userId: String?, addChatGroupFriendRequest: AddChatGroupFriendRequest): BaseResponse

}
