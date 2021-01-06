package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object ChatGroupDetails : Table(name = DatabaseConstant.CHAT_GROUP_DETAIL_TABLE) {

    val chatGroupDetailId = integer(name = DatabaseConstant.CHAT_GROUP_DETAIL_ID).autoIncrement()
    val chatGroupId = integer(name = DatabaseConstant.CHAT_GROUP_ID).references(ChatGroups.chatGroupId)
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(chatGroupDetailId, name = DatabaseConstant.CHAT_GROUP_DETAIL_PK)

}
