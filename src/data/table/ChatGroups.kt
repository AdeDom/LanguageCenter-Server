package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object ChatGroups : Table(name = DatabaseConstant.CHAT_GROUP_TABLE) {

    val chatGroupId = integer(name = DatabaseConstant.CHAT_GROUP_ID).autoIncrement()
    val groupName = varchar(name = DatabaseConstant.GROUP_NAME, length = 100)
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)
    val created = long(name = DatabaseConstant.CREATED)
    val updated = long(name = DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(chatGroupId, name = DatabaseConstant.CHAT_GROUP_PK)

}
