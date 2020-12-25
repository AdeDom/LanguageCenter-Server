package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object Users : Table(name = DatabaseConstant.USER_TABLE) {

    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50)
    val email = varchar(name = DatabaseConstant.EMAIL, length = 100).nullable()
    val givenName = varchar(name = DatabaseConstant.GIVEN_NAME, length = 100).nullable()
    val familyName = varchar(name = DatabaseConstant.FAMILY_NAME, length = 100).nullable()
    val name = varchar(name = DatabaseConstant.NAME, length = 150).nullable()
    val picture = varchar(name = DatabaseConstant.PICTURE, length = 300).nullable()
    val gender = varchar(name = DatabaseConstant.GENDER, length = 5).nullable()
    val birthDate = long(name = DatabaseConstant.BIRTH_DATE).nullable()
    val verifiedEmail = bool(name = DatabaseConstant.VERIFIED_EMAIL).nullable()
    val aboutMe = varchar(name = DatabaseConstant.ABOUT_ME, length = 500).nullable()
    val isUpdateProfile = bool(name = DatabaseConstant.IS_UPDATE_PROFILE)
    val created = long(name = DatabaseConstant.CREATED)
    val updated = long(name = DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(userId, name = DatabaseConstant.USER_PK)

}
