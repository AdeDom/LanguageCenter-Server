package com.lc.server.data.repository

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

    fun getUserInfoCommunity(userId: String): List<UserInfoDb>

    fun addAlgorithm(userId: String, addAlgorithmRequest: AddAlgorithmRequest): Boolean

    fun addChatGroupNew(userId: String, addChatGroupNewRequest: AddChatGroupNewRequest): Boolean

    fun addChatGroup(userId: String, addChatGroupRequest: AddChatGroupRequest): Boolean

    fun fetchChatGroup(userId: String): List<ChatGroup>

    fun fetchChatGroupDetail(chatGroupId: Int): List<UserInfoDb>

    fun fetchUserLocale(): List<UserLocaleDb>

    fun renameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): Boolean

    fun removeChatGroup(chatGroupId: Int)

    fun getCommunityUsers(): List<CommunityUsersDb>

    fun getCommunityUserLocales(): List<CommunityUserLocalesDb>

    fun getCommunityAlgorithms(): List<CommunityAlgorithmsDb>

    fun getCommunityChatGroups(): List<CommunityChatGroupsDb>

    fun getCommunityChatGroupDetails(): List<CommunityChatGroupDetailsDb>

}
