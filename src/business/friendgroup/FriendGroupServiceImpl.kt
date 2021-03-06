package com.lc.server.business.friendgroup

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.model.CommunityUsersDb
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.AddChatGroupDetail
import com.lc.server.models.model.ChatGroupDetail
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.*
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchAddChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupDetailResponse
import com.lc.server.models.response.FetchChatGroupResponse
import com.lc.server.util.LanguageCenterConstant
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class FriendGroupServiceImpl(
    private val repository: ServerRepository,
    private val business: ServerBusiness,
) : FriendGroupService {

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

    override fun fetchChatGroupDetail(fetchChatGroupDetailRequest: FetchChatGroupDetailRequest): FetchChatGroupDetailResponse {
        val response = FetchChatGroupDetailResponse()
        val (_, chatGroupId) = fetchChatGroupDetailRequest

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
                val addChatGroupDetailList = communityUsers.map { userInfo ->
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
                        created = userInfo.created,
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

    override fun changeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse {
        val response = BaseResponse()
        val (chatGroupId, friendUserId, changeChatGroupId) = changeChatGroupRequest

        val message: String = when {
            // validate Null Or Blank
            chatGroupId == null -> "Null"
            friendUserId.isNullOrBlank() -> "isNullOrBlank"
            changeChatGroupId == null -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.changeChatGroup(changeChatGroupRequest)
                "Change chat group success"
            }
        }

        response.message = message
        return response
    }

    override fun removeChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse {
        val response = BaseResponse()
        val (chatGroupId, friendUserId) = removeChatGroupDetailRequest

        val message: String = when {
            // validate Null Or Blank
            chatGroupId == null -> "Null"
            friendUserId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.removeChatGroupDetail(removeChatGroupDetailRequest)
                "Remove chat group detail success"
            }
        }

        response.message = message
        return response
    }

    override fun addChatGroupFriend(
        userId: String?,
        addChatGroupFriendRequest: AddChatGroupFriendRequest
    ): BaseResponse {
        val response = BaseResponse()
        val (chatGroupId, friendUserId) = addChatGroupFriendRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"
            friendUserId.isNullOrBlank() -> "isNullOrBlank"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val result: Boolean = if (chatGroupId == null) {
                    val request = AddChatGroupNewRequest(friendUserId)
                    repository.addChatGroupNew(userId, request)
                } else {
                    val request = AddChatGroupDetailRequest(
                        chatGroupId = chatGroupId,
                        userId = friendUserId,
                    )
                    repository.addChatGroupDetail(request)
                }

                response.success = result
                "Add chat group friend success"
            }
        }

        response.message = message
        return response
    }

}
