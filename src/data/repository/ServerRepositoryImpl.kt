package com.lc.server.data.repository

import com.lc.server.data.map.Mapper
import com.lc.server.data.model.UserInfoDb
import com.lc.server.data.model.UserLocaleDb
import com.lc.server.data.table.*
import com.lc.server.models.model.ChatGroup
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.*
import com.lc.server.util.LanguageCenterConstant
import io.ktor.locations.*
import models.response.GoogleApiUserInfoResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@KtorExperimentalLocationsAPI
internal class ServerRepositoryImpl : ServerRepository {

    override fun signIn(googleApiUserInfo: GoogleApiUserInfoResponse): Boolean {
        val (email, familyName, givenName, id, locale, name, picture, verifiedEmail) = googleApiUserInfo

        val count = transaction {
            id?.let {
                Users.select { Users.userId eq id }
                    .count()
                    .toInt()
            }
        }

        return if (count == 0) {
            val statement = transaction {
                val uuid = UUID.randomUUID().toString().replace("-", "")

                Users.insert {
                    it[Users.userId] = id ?: uuid
                    it[Users.email] = email
                    it[Users.givenName] = givenName
                    it[Users.familyName] = familyName
                    it[Users.name] = name
                    it[Users.picture] = picture
                    it[Users.verifiedEmail] = verifiedEmail
                    it[Users.isUpdateProfile] = false
                    it[Users.created] = System.currentTimeMillis()
                }

                UserLocales.insert {
                    it[UserLocales.userId] = id ?: uuid
                    it[UserLocales.locale] = locale
                    it[UserLocales.level] = 0
                    it[UserLocales.localeType] = LanguageCenterConstant.LOCALE_NATIVE
                }
            }

            statement.resultedValues?.size == 1
        } else {
            true
        }
    }

    override fun isUpdateProfile(id: String): Boolean {
        return transaction {
            Users.slice(Users.isUpdateProfile)
                .select { Users.userId eq id }
                .map { it[Users.isUpdateProfile] }
                .single()
        }
    }

    override fun fetchUserInfo(userId: String): UserInfoDb {
        return transaction {
            val userInfo = Users
                .select { Users.userId eq userId }
                .map { Mapper.toUserInfoDb(it) }
                .single()

            val localNatives = UserLocales
                .slice(UserLocales.locale, UserLocales.level)
                .select { UserLocales.userId eq userId }
                .andWhere { UserLocales.localeType eq LanguageCenterConstant.LOCALE_NATIVE }
                .map { UserInfoLocale(it[UserLocales.locale], it[UserLocales.level]) }

            val localLearnings = UserLocales
                .slice(UserLocales.locale, UserLocales.level)
                .select { UserLocales.userId eq userId }
                .andWhere { UserLocales.localeType eq LanguageCenterConstant.LOCALE_LEARNING }
                .map { UserInfoLocale(it[UserLocales.locale], it[UserLocales.level]) }

            userInfo.copy(localNatives = localNatives, localLearnings = localLearnings)
        }
    }

    override fun guideUpdateProfile(userId: String, guideUpdateProfileRequest: GuideUpdateProfileRequest): Boolean {
        val (localNatives, localLearnings, gender, birthDate) = guideUpdateProfileRequest

        val result = transaction {
            UserLocales.deleteWhere { UserLocales.userId eq userId }

            // UserLocaleNatives
            UserLocales.batchInsert(localNatives) { (locale, level) ->
                this[UserLocales.userId] = userId
                this[UserLocales.locale] = locale
                this[UserLocales.level] = level
                this[UserLocales.localeType] = LanguageCenterConstant.LOCALE_NATIVE
            }

            // UserLocaleLearnings
            UserLocales.batchInsert(localLearnings) { (locale, level) ->
                this[UserLocales.userId] = userId
                this[UserLocales.locale] = locale
                this[UserLocales.level] = level
                this[UserLocales.localeType] = LanguageCenterConstant.LOCALE_LEARNING
            }

            // Users
            Users.update({ Users.userId eq userId }) {
                it[Users.gender] = gender
                it[Users.birthDate] = birthDate
                it[Users.isUpdateProfile] = true
                it[Users.updated] = System.currentTimeMillis()
            }
        }

        return result == 1
    }

    override fun editProfile(userId: String, editProfileRequest: EditProfileRequest): Boolean {
        val (givenName, familyName, gender, birthDate, aboutMe) = editProfileRequest

        val result = transaction {
            Users.update({ Users.userId eq userId }) {
                it[Users.givenName] = givenName
                it[Users.familyName] = familyName
                it[Users.gender] = gender
                it[Users.birthDate] = birthDate
                it[Users.aboutMe] = aboutMe
                it[Users.updated] = System.currentTimeMillis()
            }
        }

        return result == 1
    }

    override fun editLocaleNative(userId: String, locales: List<UserInfoLocale>): Boolean {
        val result = transaction {
            UserLocales.deleteWhere { UserLocales.userId eq userId and (UserLocales.localeType eq LanguageCenterConstant.LOCALE_NATIVE) }

            UserLocales.batchInsert(locales) { (locale, level) ->
                this[UserLocales.userId] = userId
                this[UserLocales.locale] = locale
                this[UserLocales.level] = level
                this[UserLocales.localeType] = LanguageCenterConstant.LOCALE_NATIVE
            }

            Users.update({ Users.userId eq userId }) {
                it[Users.updated] = System.currentTimeMillis()
            }
        }

        return result == 1
    }

    override fun editLocaleLearning(userId: String, locales: List<UserInfoLocale>): Boolean {
        val result = transaction {
            UserLocales.deleteWhere { UserLocales.userId eq userId and (UserLocales.localeType eq LanguageCenterConstant.LOCALE_LEARNING) }

            UserLocales.batchInsert(locales) { (locale, level) ->
                this[UserLocales.userId] = userId
                this[UserLocales.locale] = locale
                this[UserLocales.level] = level
                this[UserLocales.localeType] = LanguageCenterConstant.LOCALE_LEARNING
            }

            Users.update({ Users.userId eq userId }) {
                it[Users.updated] = System.currentTimeMillis()
            }
        }

        return result == 1
    }

    override fun getUserInfoCommunity(userId: String): List<UserInfoDb> {
        return transaction {
            Users.slice(
                Users.userId,
                Users.email,
                Users.givenName,
                Users.familyName,
                Users.name,
                Users.picture,
                Users.gender,
                Users.birthDate,
                Users.verifiedEmail,
                Users.aboutMe,
                Users.created,
                Users.updated,
            ).select { Users.userId neq userId }
                .map { Mapper.toUserInfoDb(it) }
        }
    }

    override fun getUserLocaleCommunity(userId: String): List<UserLocaleDb> {
        return transaction {
            UserLocales.slice(
                UserLocales.userId,
                UserLocales.locale,
                UserLocales.level,
                UserLocales.localeType,
            ).select { UserLocales.userId neq userId }
                .map { Mapper.toUserLocaleDb(it) }
        }
    }

    override fun addAlgorithm(userId: String, addAlgorithmRequest: AddAlgorithmRequest): Boolean {
        val (algorithm) = addAlgorithmRequest

        val statement = transaction {
            Algorithms.insert {
                it[Algorithms.userId] = userId
                it[Algorithms.algorithm] = algorithm!!
                it[Algorithms.created] = System.currentTimeMillis()
            }
        }

        return statement.resultedValues?.size == 1
    }

    override fun addChatGroupNew(userId: String, addChatGroupNewRequest: AddChatGroupNewRequest): Boolean {
        val result = transaction {
            // chat group
            val countChatGroup = ChatGroups.select { ChatGroups.userId eq userId }
                .andWhere { ChatGroups.groupName eq LanguageCenterConstant.CHAT_GROUP_NAME_NEW }
                .count()
                .toInt()

            if (countChatGroup == 0) {
                ChatGroups.insert {
                    it[ChatGroups.groupName] = LanguageCenterConstant.CHAT_GROUP_NAME_NEW
                    it[ChatGroups.userId] = userId
                }
            }

            // chat group detail
            val chatGroupDetailId = (ChatGroups innerJoin ChatGroupDetails)
                .slice(ChatGroupDetails.chatGroupDetailId)
                .select { ChatGroupDetails.userId eq addChatGroupNewRequest.userId!! }
                .map { it[ChatGroupDetails.chatGroupDetailId] }
                .singleOrNull()

            val chatGroupId = ChatGroups
                .slice(ChatGroups.chatGroupId)
                .select { ChatGroups.userId eq userId }
                .andWhere { ChatGroups.groupName eq LanguageCenterConstant.CHAT_GROUP_NAME_NEW }
                .map { it[ChatGroups.chatGroupId] }
                .single()

            if (chatGroupDetailId == null) {
                val result = ChatGroupDetails.insert {
                    it[ChatGroupDetails.chatGroupId] = chatGroupId
                    it[ChatGroupDetails.userId] = addChatGroupNewRequest.userId!!
                }

                result.resultedValues?.size
            } else {
                ChatGroupDetails.update({ ChatGroupDetails.chatGroupDetailId eq chatGroupDetailId }) {
                    it[ChatGroupDetails.chatGroupId] = chatGroupId
                }
            }
        }

        return result == 1
    }

    override fun addChatGroup(userId: String, addChatGroupRequest: AddChatGroupRequest): Boolean {
        val (groupName) = addChatGroupRequest

        val statement = transaction {
            ChatGroups.insert {
                it[ChatGroups.groupName] = groupName!!
                it[ChatGroups.userId] = userId
            }
        }

        return statement.resultedValues?.size == 1
    }

    override fun fetchChatGroup(userId: String): List<ChatGroup> {
        return transaction {
            ChatGroups
                .slice(
                    ChatGroups.chatGroupId,
                    ChatGroups.groupName,
                    ChatGroups.userId,
                )
                .select { ChatGroups.userId eq userId }
                .map { Mapper.toChatGroupDb(it) }
        }
    }

}
