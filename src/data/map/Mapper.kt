package com.lc.server.data.map

import com.lc.server.data.model.*
import com.lc.server.data.table.*
import com.lc.server.models.model.ChatGroup
import org.jetbrains.exposed.sql.ResultRow

object Mapper {

    fun toUserInfoDb(row: ResultRow) = UserInfoDb(
        userId = row[Users.userId],
        email = row[Users.email],
        givenName = row[Users.givenName],
        familyName = row[Users.familyName],
        name = row[Users.name],
        picture = row[Users.picture],
        gender = row[Users.gender],
        birthDate = row[Users.birthDate],
        verifiedEmail = row[Users.verifiedEmail],
        aboutMe = row[Users.aboutMe],
        created = row[Users.created],
        updated = row[Users.updated],
    )

    fun toUserLocaleDb(row: ResultRow) = UserLocaleDb(
        userId = row[UserLocales.userId],
        locale = row[UserLocales.locale],
        level = row[UserLocales.level],
        localeType = row[UserLocales.localeType],
    )

    fun toChatGroupDb(row: ResultRow) = ChatGroup(
        chatGroupId = row[ChatGroups.chatGroupId],
        groupName = row[ChatGroups.groupName],
        userId = row[ChatGroups.userId],
    )

    fun toCommunityAlgorithmsDb(row: ResultRow) = CommunityAlgorithmsDb(
        algorithmId = row[Algorithms.algorithmId],
        userId = row[Algorithms.userId],
        algorithm = row[Algorithms.algorithm],
        created = row[Algorithms.created],
    )

    fun toCommunityChatGroupDetailsDb(row: ResultRow) = CommunityChatGroupDetailsDb(
        chatGroupDetailId = row[ChatGroupDetails.chatGroupDetailId],
        chatGroupId = row[ChatGroupDetails.chatGroupId],
        userId = row[ChatGroupDetails.userId],
        created = row[ChatGroupDetails.created],
        updated = row[ChatGroupDetails.updated],
    )

    fun toCommunityChatGroupsDb(row: ResultRow) = CommunityChatGroupsDb(
        chatGroupId = row[ChatGroups.chatGroupId],
        groupName = row[ChatGroups.groupName],
        userId = row[ChatGroups.userId],
        created = row[ChatGroups.created],
        updated = row[ChatGroups.updated],
    )

    fun toCommunityUserLocalesDb(row: ResultRow) = CommunityUserLocalesDb(
        localeId = row[UserLocales.localeId],
        userId = row[UserLocales.userId],
        locale = row[UserLocales.locale],
        level = row[UserLocales.level],
        localeType = row[UserLocales.localeType],
        created = row[UserLocales.created],
    )

    fun toCommunityUsersDb(row: ResultRow) = CommunityUsersDb(
        userId = row[Users.userId],
        email = row[Users.email],
        givenName = row[Users.givenName],
        familyName = row[Users.familyName],
        name = row[Users.name],
        picture = row[Users.picture],
        gender = row[Users.gender],
        birthDate = row[Users.birthDate],
        verifiedEmail = row[Users.verifiedEmail],
        aboutMe = row[Users.aboutMe],
        isUpdateProfile = row[Users.isUpdateProfile],
        created = row[Users.created],
        updated = row[Users.updated],
    )

}
