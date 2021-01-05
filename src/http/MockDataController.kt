package com.lc.server.http

import com.lc.server.data.table.UserLocales
import com.lc.server.data.table.Users
import com.lc.server.getHttpClientApache
import com.lc.server.models.response.BaseResponse
import com.lc.server.util.LanguageCenterConstant
import com.lc.server.util.fromJson
import io.ktor.application.*
import io.ktor.client.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.mockdata.RandomUserResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Route.mockDataController() {

    post("/api/mock-data/random-user") {
        val response = BaseResponse()

        try {
            val (info, results) = getHttpClientApache()
                .get<String>("https://randomuser.me/api/")
                .fromJson<RandomUserResponse>()

            val result = results.singleOrNull()

            transaction {
                // user
                val userId = info?.seed ?: UUID.randomUUID().toString().replace("-", "")
                Users.replace {
                    it[Users.userId] = userId
                    it[Users.email] = result?.email
                    it[Users.givenName] = result?.name?.first
                    it[Users.familyName] = result?.name?.last
                    it[Users.name] = "${result?.name?.first} ${result?.name?.last}"
                    it[Users.picture] = result?.picture?.large
                    it[Users.gender] = getGender(result?.gender)
                    it[Users.birthDate] = getAgeLong(result?.dob?.date)
                    it[Users.verifiedEmail] = false
                    it[Users.aboutMe] = info?.seed
                    it[Users.isUpdateProfile] = false
                    it[Users.created] = System.currentTimeMillis()
                }

                // user locale native
                repeat((1..2).random()) {
                    when ((1..2).random()) {
                        1 -> UserLocales.insert {
                            it[UserLocales.userId] = userId
                            it[UserLocales.locale] = LanguageCenterConstant.LOCALE_THAI
                            it[UserLocales.level] = (0..100).random()
                            it[UserLocales.localeType] = LanguageCenterConstant.LOCALE_NATIVE
                        }
                        2 -> UserLocales.insert {
                            it[UserLocales.userId] = userId
                            it[UserLocales.locale] = LanguageCenterConstant.LOCALE_ENGLISH
                            it[UserLocales.level] = (0..100).random()
                            it[UserLocales.localeType] = LanguageCenterConstant.LOCALE_NATIVE
                        }
                    }
                }

                // user locale learning
                repeat((1..2).random()) {
                    when ((1..2).random()) {
                        1 -> UserLocales.insert {
                            it[UserLocales.userId] = userId
                            it[UserLocales.locale] = LanguageCenterConstant.LOCALE_THAI
                            it[UserLocales.level] = (0..100).random()
                            it[UserLocales.localeType] = LanguageCenterConstant.LOCALE_LEARNING
                        }
                        2 -> UserLocales.insert {
                            it[UserLocales.userId] = userId
                            it[UserLocales.locale] = LanguageCenterConstant.LOCALE_ENGLISH
                            it[UserLocales.level] = (0..100).random()
                            it[UserLocales.localeType] = LanguageCenterConstant.LOCALE_LEARNING
                        }
                    }
                }
            }

            response.success = true
            response.message = "Mock data success"
        } catch (e: Throwable) {
//            ServerResponseException
            response.success = false
            response.message = e.message
        }

        call.respond(response)
    }

}

private fun getGender(gender: String?): String? = when (gender) {
    "male" -> LanguageCenterConstant.GENDER_MALE
    "female" -> LanguageCenterConstant.GENDER_FEMALE
    else -> null
}

private fun getAgeLong(str: String?): Long {
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val time = format.parse(str).time

    return time
}

private fun getAgeInt(str: String?): Int {
    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val time = format.parse(str).time

    val now = System.currentTimeMillis()
    val timeBetween: Long = now - time
    val yearsBetween: Double = timeBetween / 3.15576e+10
    val age = kotlin.math.floor(yearsBetween).toInt()

    return age
}

private fun getAgeInt(time: Long): Int {
    val now = System.currentTimeMillis()
    val timeBetween: Long = now - time
    val yearsBetween: Double = timeBetween / 3.15576e+10
    val age = kotlin.math.floor(yearsBetween).toInt()

    return age
}
