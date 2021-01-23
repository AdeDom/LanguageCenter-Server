package com.lc.server.business.chats

import com.lc.server.models.request.*
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.ChatListUserInfoResponse
import com.lc.server.models.response.FetchTalkUnreceivedResponse
import com.lc.server.models.response.SendMessageResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface ChatsService {

    fun sendMessage(userId: String?, sendMessageRequest: SendMessageRequest): SendMessageResponse

    fun chatListUserInfo(chatListUserInfoRequest: ChatListUserInfoRequest): ChatListUserInfoResponse

    fun readMessages(userId: String?, readMessagesRequest: ReadMessagesRequest): BaseResponse

    fun receiveMessage(receiveMessageRequest: ReceiveMessageRequest): BaseResponse

    fun resendMessage(resendMessageRequest: ResendMessageRequest): BaseResponse

    fun fetchTalkUnreceived(userId: String?): FetchTalkUnreceivedResponse

}
