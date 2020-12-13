package com.lc.server.data.map

import com.lc.server.data.model.UserInfoDb
import com.lc.server.data.table.Users
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

}
