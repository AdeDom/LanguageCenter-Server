package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object UserLocaleNatives : Table(name = DatabaseConstant.USER_LOCALE_NATIVE_TABLE) {

    val nativeId = integer(name = DatabaseConstant.NATIVE_ID)
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50)
    val locale = varchar(name = DatabaseConstant.LOCALE, length = 5).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(nativeId, name = DatabaseConstant.USER_LOCALE_NATIVE_PK)

}
