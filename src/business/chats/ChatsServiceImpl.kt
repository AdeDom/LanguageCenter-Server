package com.lc.server.business.chats

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.ChatListUserInfo
import com.lc.server.models.request.ChatListUserInfoRequest
import com.lc.server.models.request.ReadMessagesRequest
import com.lc.server.models.request.ReceiveMessageRequest
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.ChatListUserInfoResponse
import com.lc.server.models.response.SendMessageResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class ChatsServiceImpl(
    private val repository: ServerRepository,
    private val business: ServerBusiness,
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

            // execute
            else -> {
                response.success = repository.sendMessage(userId, sendMessageRequest, dateTimeLong)
                "Send message success"
            }
        }

        response.message = message
        return response
    }

    override fun chatListUserInfo(chatListUserInfoRequest: ChatListUserInfoRequest): ChatListUserInfoResponse {
        val response = ChatListUserInfoResponse()
        val (_, otherUserId) = chatListUserInfoRequest

        val message: String = when {
            // validate Null Or Blank
            otherUserId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val db = repository.fetchUserInfo(otherUserId)
                val chatListUserInfo = ChatListUserInfo(
                    userId = db.userId,
                    email = db.email,
                    givenName = db.givenName?.capitalize(),
                    familyName = db.familyName?.capitalize(),
                    name = db.name?.capitalize(),
                    picture = db.picture,
                    gender = db.gender,
                    age = db.birthDate?.let { business.getAgeInt(it) },
                    birthDateString = business.convertDateTimeLongToString(db.birthDate),
                    birthDateLong = db.birthDate,
                    aboutMe = db.aboutMe,
                    localNatives = db.localNatives,
                    localLearnings = db.localLearnings,
                )

                response.success = true
                response.chatListUserInfo = chatListUserInfo
                "Chat list user info success"
            }
        }

        response.message = message
        return response
    }

    override fun readMessages(userId: String?, readMessagesRequest: ReadMessagesRequest): BaseResponse {
        val response = BaseResponse()
        val (readUserId) = readMessagesRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"
            readUserId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.readMessages(userId, readMessagesRequest)
                "Read message success"
            }
        }

        response.message = message
        return response
    }

    override fun receiveMessage(receiveMessageRequest: ReceiveMessageRequest): BaseResponse {
        val response = BaseResponse()
        val (talkId) = receiveMessageRequest

        val message: String = when {
            // validate Null Or Blank
            talkId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.receiveMessage(receiveMessageRequest)
                "Update send message success"
            }
        }

        response.message = message
        return response
    }

}
