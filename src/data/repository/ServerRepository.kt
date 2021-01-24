package com.lc.server.data.repository

import com.lc.server.business.model.CommunityAlgorithm
import com.lc.server.data.model.*
import com.lc.server.models.model.ChatGroup
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.*
import io.ktor.locations.*
import models.response.GoogleApiUserInfoResponse

@KtorExperimentalLocationsAPI
internal interface ServerRepository {

    fun signIn(googleApiUserInfo: GoogleApiUserInfoResponse): Boolean

    fun isUpdateProfile(id: String): Boolean

    fun fetchUserInfo(userId: String): UserInfoDb

    fun guideUpdateProfile(userId: String, guideUpdateProfileRequest: GuideUpdateProfileRequest): Boolean

    fun editProfile(userId: String, editProfileRequest: EditProfileRequest): Boolean

    fun editLocaleNative(userId: String, locales: List<UserInfoLocale>): Boolean

    fun editLocaleLearning(userId: String, locales: List<UserInfoLocale>): Boolean

    fun addAlgorithm(userId: String, addAlgorithmRequest: AddAlgorithmRequest): Boolean

    fun addChatGroupNew(userId: String, addChatGroupNewRequest: AddChatGroupNewRequest): Boolean

    fun addChatGroup(userId: String, addChatGroupRequest: AddChatGroupRequest): Boolean

    fun fetchChatGroup(userId: String): List<ChatGroup>

    fun fetchChatGroupDetail(chatGroupId: Int): List<UserInfoDb>

    fun fetchUserLocale(): List<UserLocaleDb>

    fun renameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): Boolean

    fun removeChatGroup(chatGroupId: Int)

    fun getCommunityUsers(userId: String): List<CommunityUsersDb>

    fun getCommunityUserLocales(): List<CommunityUserLocalesDb>

    fun getCommunityAlgorithms(userId: String): List<CommunityAlgorithm>

    fun getCommunityFriend(userId: String): List<String>

    fun getCommunityMyBirthDate(userId: String): Long?

    fun addChatGroupDetail(addChatGroupDetailRequest: AddChatGroupDetailRequest): Boolean

    fun changeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): Boolean

    fun removeChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Boolean

    fun sendMessage(userId: String, sendMessageRequest: SendMessageRequest, dateTimeLong: Long): Boolean

    fun readMessages(userId: String, readMessagesRequest: ReadMessagesRequest): Boolean

    fun receiveMessage(receiveMessageRequest: ReceiveMessageRequest): Boolean

    fun resendMessage(resendMessageRequest: ResendMessageRequest): Boolean

    fun fetchTalkUnreceived(userId: String): List<TalkDb>

    fun updateReceiveMessage(updateReceiveMessageRequest: UpdateReceiveMessageRequest): Boolean

}
