package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object UserLocales : Table(name = DatabaseConstant.USER_LOCALE_TABLE) {

    val localeId = integer(name = DatabaseConstant.LOCALE_ID).autoIncrement()
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)
    val locale = varchar(name = DatabaseConstant.LOCALE, length = 5).nullable()
    val level = integer(name = DatabaseConstant.LEVEL)
    val localeType = varchar(name = DatabaseConstant.LOCALE_TYPE, length = 10)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(localeId, name = DatabaseConstant.USER_LOCALE_PK)

}
