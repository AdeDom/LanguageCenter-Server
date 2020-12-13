package com.lc.server.data.repository

import com.lc.server.data.table.UserLocaleNatives
import com.lc.server.data.table.Users
import models.response.GoogleApiUserInfoResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ServerRepositoryImpl : ServerRepository {

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

}
