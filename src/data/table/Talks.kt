package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object Talks : Table(name = DatabaseConstant.TALK_TABLE) {

    val talkId = varchar(name = DatabaseConstant.TALK_ID, length = 50)
    val fromUserId = varchar(name = DatabaseConstant.FROM_USER_ID, length = 50)
    val toUserId = varchar(name = DatabaseConstant.TO_USER_ID, length = 50)
    val messages = varchar(name = DatabaseConstant.MESSAGES, length = 10000) // 65535
    val dateTime = long(name = DatabaseConstant.DATE_TIME)
    val isRead = bool(name = DatabaseConstant.IS_READ)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(talkId, name = DatabaseConstant.TALK_PK)

}