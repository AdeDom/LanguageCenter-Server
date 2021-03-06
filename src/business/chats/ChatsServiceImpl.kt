package com.lc.server.business.chats

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.ChatListUserInfo
import com.lc.server.models.model.Talk
import com.lc.server.models.model.Translation
import com.lc.server.models.model.Vocabulary
import com.lc.server.models.request.*
import com.lc.server.models.response.*
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
                "Receive message success"
            }
        }

        response.message = message
        return response
    }

    override fun resendMessage(resendMessageRequest: ResendMessageRequest): BaseResponse {
        val response = BaseResponse()
        val (talkId, fromUserId, toUserId, messages, dateTimeLong) = resendMessageRequest

        val message: String = when {
            // validate Null Or Blank
            talkId.isNullOrBlank() -> "isNullOrBlank"
            fromUserId.isNullOrBlank() -> "isNullOrBlank"
            toUserId.isNullOrBlank() -> "isNullOrBlank"
            messages.isNullOrBlank() -> "isNullOrBlank"
            dateTimeLong == null -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.resendMessage(resendMessageRequest)
                "Resend message success"
            }
        }

        response.message = message
        return response
    }

    override fun fetchTalkUnreceived(userId: String?): FetchTalkUnreceivedResponse {
        val response = FetchTalkUnreceivedResponse()

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val talkUnreceivedList = repository.fetchTalkUnreceived(userId)
                val talks = talkUnreceivedList.map {
                    Talk(
                        talkId = it.talkId,
                        fromUserId = it.fromUserId,
                        toUserId = it.toUserId,
                        messages = it.messages,
                        isSendMessage = it.isSendMessage,
                        isReceiveMessage = it.isReceiveMessage,
                        isRead = it.isRead,
                        isShow = it.isShow,
                        dateTime = it.dateTime,
                        dateTimeUpdated = it.dateTimeUpdated,
                    )
                }
                response.talks = talks
                response.success = true
                "Fetch talk unreceived success"
            }
        }

        response.message = message
        return response
    }

    override fun updateReceiveMessage(updateReceiveMessageRequest: UpdateReceiveMessageRequest): BaseResponse {
        val response = BaseResponse()
        val (talkIdList) = updateReceiveMessageRequest

        val message: String = when {
            // validate Null Or Blank

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = if (talkIdList.isNullOrEmpty()) {
                    true
                } else {
                    repository.updateReceiveMessage(updateReceiveMessageRequest)
                }

                "Update receive message success"
            }
        }

        response.message = message
        return response
    }

    override fun languageCenterTranslate(languageCenterTranslateRequest: LanguageCenterTranslateRequest): LanguageCenterTranslateResponse {
        val response = LanguageCenterTranslateResponse()
        val (_, vocabularySearch) = languageCenterTranslateRequest

        val message: String = when {
            // validate Null Or Blank
            vocabularySearch.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val languageCenterTranslateDb = repository.languageCenterTranslate(languageCenterTranslateRequest)

                // vocabulary
                val vocabulary = languageCenterTranslateDb
                    .distinctBy { it.vocabularyId }
                    .map { db ->
                        // translation list
                        val translations = languageCenterTranslateDb
                            .filter { it.translationIdToVocabularyId == db.vocabularyId }
                            .map {
                                // map translation
                                Translation(
                                    translationId = it.translationId,
                                    translation = it.translation?.capitalize(),
                                    targetLanguage = it.targetLanguage,
                                )
                            }

                        // map vocabulary
                        Vocabulary(
                            vocabularyId = db.vocabularyId,
                            vocabulary = db.vocabulary.capitalize(),
                            sourceLanguage = db.sourceLanguage,
                            created = db.created,
                            vocabularyGroupName = db.vocabularyGroupName,
                            translations = translations,
                        )
                    }
                    .singleOrNull()

                response.vocabulary = vocabulary
                response.success = true
                "Language center translate success"
            }
        }

        response.message = message
        return response
    }

}
