package com.lc.server.data.map

import com.lc.server.business.model.CommunityBusiness
import com.lc.server.data.model.*
import com.lc.server.data.table.*
import com.lc.server.models.model.ChatGroup
import io.ktor.util.*
import org.jetbrains.exposed.sql.ResultRow

@InternalAPI
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

    fun toCommunityAlgorithmBusiness(db: CommunityUsersDb) = CommunityBusiness(
        userId = db.userId,
        email = db.email,
        givenName = db.givenName,
        familyName = db.familyName,
        name = db.name,
        picture = db.picture,
        gender = db.gender,
        birthDate = db.birthDate,
        verifiedEmail = db.verifiedEmail,
        aboutMe = db.aboutMe,
        isUpdateProfile = db.isUpdateProfile,
        created = db.created,
        updated = db.updated,
    )

    fun toTalkDb(row: ResultRow) = TalkDb(
        talkId = row[Talks.talkId],
        fromUserId = row[Talks.fromUserId],
        toUserId = row[Talks.toUserId],
        messages = row[Talks.messages].decodeBase64String(),
        isSendMessage = row[Talks.isSendMessage],
        isReceiveMessage = row[Talks.isReceiveMessage],
        isRead = row[Talks.isRead],
        isShow = row[Talks.isShow],
        dateTime = row[Talks.dateTime],
        dateTimeUpdated = row[Talks.dateTimeUpdated],
    )

    fun toVocabularyTranslationDb(row: ResultRow) = VocabularyTranslationDb(
        vocabularyId = row[Vocabularies.vocabularyId],
        vocabulary = row[Vocabularies.vocabulary].decodeBase64String(),
        sourceLanguage = row[Vocabularies.sourceLanguage],
        created = row[Vocabularies.created],
        vocabularyGroupName = row[VocabularyGroups.vocabularyGroupName],
        translationId = row[Translations.translationId],
        translationIdToVocabularyId = row[Translations.vocabularyId],
        translation = row[Translations.translation]?.decodeBase64String(),
        targetLanguage = row[Translations.targetLanguage],
    )

}
