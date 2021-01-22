package com.lc.server.business.chats

import com.lc.server.models.request.ChatListUserInfoRequest
import com.lc.server.models.request.ReadMessagesRequest
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.request.UpdateSendMessageRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.ChatListUserInfoResponse
import com.lc.server.models.response.SendMessageResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface ChatsService {

    fun sendMessage(userId: String?, sendMessageRequest: SendMessageRequest): SendMessageResponse

    fun chatListUserInfo(chatListUserInfoRequest: ChatListUserInfoRequest): ChatListUserInfoResponse

    fun readMessages(userId: String?, readMessagesRequest: ReadMessagesRequest): BaseResponse

    fun updateSendMessage(updateSendMessageRequest: UpdateSendMessageRequest): BaseResponse

}
