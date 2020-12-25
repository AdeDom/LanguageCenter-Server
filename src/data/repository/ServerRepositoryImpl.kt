package com.lc.server.data.repository

import com.lc.server.data.map.Mapper
import com.lc.server.data.model.UserInfoDb
import com.lc.server.data.table.UserLocaleLearnings
import com.lc.server.data.table.UserLocaleNatives
import com.lc.server.data.table.Users
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.GuideUpdateProfileRequest
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

                UserLocaleNatives.insert {
                    it[UserLocaleNatives.userId] = id ?: uuid
                    it[UserLocaleNatives.locale] = locale
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

            val localNatives = UserLocaleNatives
                .slice(UserLocaleNatives.locale, UserLocaleNatives.level)
                .select { UserLocaleNatives.userId eq userId }
                .map { UserInfoLocale(it[UserLocaleNatives.locale], it[UserLocaleNatives.level]) }

            val localLearnings = UserLocaleLearnings
                .slice(UserLocaleLearnings.locale, UserLocaleLearnings.level)
                .select { UserLocaleLearnings.userId eq userId }
                .map { UserInfoLocale(it[UserLocaleLearnings.locale], it[UserLocaleLearnings.level]) }

            userInfo.copy(localNatives = localNatives, localLearnings = localLearnings)
        }
    }

    override fun guideUpdateProfile(userId: String, guideUpdateProfileRequest: GuideUpdateProfileRequest): Boolean {
        val (localNatives, localLearnings, gender, birthDate) = guideUpdateProfileRequest

        val result = transaction {
            // UserLocaleNatives
            UserLocaleNatives.deleteWhere { UserLocaleNatives.userId eq userId }

            UserLocaleNatives.batchInsert(localNatives) { (locale, level) ->
                this[UserLocaleNatives.userId] = userId
                this[UserLocaleNatives.locale] = locale
                this[UserLocaleNatives.level] = level
            }

            // UserLocaleLearnings
            UserLocaleLearnings.deleteWhere { UserLocaleLearnings.userId eq userId }

            UserLocaleLearnings.batchInsert(localLearnings) { (locale, level) ->
                this[UserLocaleLearnings.userId] = userId
                this[UserLocaleLearnings.locale] = locale
                this[UserLocaleLearnings.level] = level
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
        val (email, givenName, familyName, gender, birthDate, aboutMe) = editProfileRequest

        val result = transaction {
            Users.update({ Users.userId eq userId }) {
                it[Users.email] = email
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

}
