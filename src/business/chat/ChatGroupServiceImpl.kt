package com.lc.server.business.chat

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.model.CommunityUsersDb
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.AddChatGroupDetail
import com.lc.server.models.model.ChatGroupDetail
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.request.RenameChatGroupRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchAddChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupResponse
import com.lc.server.util.LanguageCenterConstant
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class ChatGroupServiceImpl(
    private val repository: ServerRepository,
    private val business: ServerBusiness,
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
                response.chatGroups = repository.fetchChatGroup(userId)
                response.success = true
                "Fetch chat group success"
            }
        }

        response.message = message
        return response
    }

    override fun fetchChatGroupDetail(chatGroupId: String?): FetchChatGroupDetailResponse {
        val response = FetchChatGroupDetailResponse()

        val message: String = when {
            chatGroupId?.toIntOrNull() == null -> "Null"

            else -> {
                val userLocaleList = repository.fetchUserLocale()

                val chatGroupDetail = repository.fetchChatGroupDetail(chatGroupId.toInt()).map { userInfo ->
                    ChatGroupDetail(
                        userId = userInfo.userId,
                        email = userInfo.email,
                        givenName = userInfo.givenName?.capitalize(),
                        familyName = userInfo.familyName?.capitalize(),
                        name = userInfo.name?.capitalize(),
                        picture = userInfo.picture,
                        gender = userInfo.gender,
                        age = userInfo.birthDate?.let { business.getAgeInt(it) },
                        birthDateString = business.convertDateTimeLongToString(userInfo.birthDate),
                        birthDateLong = userInfo.birthDate,
                        aboutMe = userInfo.aboutMe,
                        localNatives = userLocaleList.filter { it.userId == userInfo.userId }
                            .filter { it.localeType == LanguageCenterConstant.LOCALE_NATIVE }
                            .map { UserInfoLocale(locale = it.locale, level = it.level) },
                        localLearnings = userLocaleList.filter { it.userId == userInfo.userId }
                            .filter { it.localeType == LanguageCenterConstant.LOCALE_LEARNING }
                            .map { UserInfoLocale(locale = it.locale, level = it.level) },
                    )
                }
                response.chatGroupDetails = chatGroupDetail
                response.success = true
                "Fetch chat group detail success"
            }
        }

        response.message = message
        return response
    }

    override fun renameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): BaseResponse {
        val response = BaseResponse()
        val (chatGroupId, groupName) = renameChatGroupRequest

        val message: String = when {
            chatGroupId == null -> "Null"
            groupName.isNullOrBlank() -> "isNullOrBlank"

            else -> {
                response.success = repository.renameChatGroup(renameChatGroupRequest)
                "Rename chat group success"
            }
        }

        response.message = message
        return response
    }

    override fun removeChatGroup(chatGroupId: String?): BaseResponse {
        val response = BaseResponse()

        val message: String = when {
            chatGroupId?.toIntOrNull() == null -> "Null"

            else -> {
                repository.removeChatGroup(chatGroupId.toInt())
                response.success = true
                "Remove chat group success"
            }
        }

        response.message = message
        return response
    }

    override fun fetchAddChatGroupDetail(userId: String?): FetchAddChatGroupDetailResponse {
        val response = FetchAddChatGroupDetailResponse()

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                // data
                val users = repository.getCommunityUsers(userId)
                val userLocales = repository.getCommunityUserLocales()
                val friend = repository.getCommunityFriend(userId)

                // filter other friend
                val list = mutableListOf<CommunityUsersDb>()
                friend.forEach { friendId ->
                    users.filter { it.userId == friendId }
                        .onEach { list.add(it) }
                }
                val communityUsers = users - list

                // map
                val addChatGroupDetailList = communityUsers.map { userInfo->
                    AddChatGroupDetail(
                        userId = userInfo.userId,
                        email = userInfo.email,
                        givenName = userInfo.givenName?.capitalize(),
                        familyName = userInfo.familyName?.capitalize(),
                        name = userInfo.name?.capitalize(),
                        picture = userInfo.picture,
                        gender = userInfo.gender,
                        age = userInfo.birthDate?.let { business.getAgeInt(it) },
                        birthDateString = business.convertDateTimeLongToString(userInfo.birthDate),
                        birthDateLong = userInfo.birthDate,
                        aboutMe = userInfo.aboutMe,
                        localNatives = userLocales.filter { it.userId == userInfo.userId }
                            .filter { it.localeType == LanguageCenterConstant.LOCALE_NATIVE }
                            .map { UserInfoLocale(locale = it.locale, level = it.level) },
                        localLearnings = userLocales.filter { it.userId == userInfo.userId }
                            .filter { it.localeType == LanguageCenterConstant.LOCALE_LEARNING }
                            .map { UserInfoLocale(locale = it.locale, level = it.level) },
                    )
                }
                response.addChatGroupDetailList = addChatGroupDetailList
                response.success = true
                "Fetch add chat group detail success"
            }
        }

        response.message = message
        return response
    }

}
