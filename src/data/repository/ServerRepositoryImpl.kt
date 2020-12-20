package com.lc.server.data.repository

import com.lc.server.data.map.Mapper
import com.lc.server.data.model.UserInfoDb
import com.lc.server.data.table.UserLocaleNatives
import com.lc.server.data.table.Users
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.EditProfileRequest
import io.ktor.locations.*
import models.response.GoogleApiUserInfoResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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

    override fun fetchUserInfo(userId: String): UserInfoDb {
        return transaction {
            val userInfo = Users
                .select { Users.userId eq userId }
                .map { Mapper.toUserInfoDb(it) }
                .single()

            val locales = UserLocaleNatives
                .slice(UserLocaleNatives.locale)
                .select { UserLocaleNatives.userId eq userId }
                .map { UserInfoLocale(it[UserLocaleNatives.locale]) }

            userInfo.copy(locales = locales)
        }
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
