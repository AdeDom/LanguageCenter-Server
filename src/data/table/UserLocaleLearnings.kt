package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object UserLocaleLearnings : Table(name = DatabaseConstant.USER_LOCALE_LEARNING_TABLE) {

    val learningId = integer(name = DatabaseConstant.LEARNING_ID)
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50)
    val locale = varchar(name = DatabaseConstant.LOCALE, length = 5).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(learningId, name = DatabaseConstant.USER_LOCALE_LEARNING_PK)

}
