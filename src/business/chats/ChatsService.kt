package com.lc.server.business.chats

import com.lc.server.models.request.*
import com.lc.server.models.response.*
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface ChatsService {

    fun sendMessage(userId: String?, sendMessageRequest: SendMessageRequest): SendMessageResponse

    fun chatListUserInfo(chatListUserInfoRequest: ChatListUserInfoRequest): ChatListUserInfoResponse

    fun readMessages(userId: String?, readMessagesRequest: ReadMessagesRequest): BaseResponse

    fun receiveMessage(receiveMessageRequest: ReceiveMessageRequest): BaseResponse

    fun resendMessage(resendMessageRequest: ResendMessageRequest): BaseResponse

    fun fetchTalkUnreceived(userId: String?): FetchTalkUnreceivedResponse

    fun updateReceiveMessage(updateReceiveMessageRequest: UpdateReceiveMessageRequest): BaseResponse

    fun languageCenterTranslate(languageCenterTranslateRequest: LanguageCenterTranslateRequest): LanguageCenterTranslateResponse

}
