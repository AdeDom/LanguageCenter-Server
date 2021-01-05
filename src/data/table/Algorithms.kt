package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object Algorithms : Table(name = DatabaseConstant.ALGORITHM_TABLE) {

    val algorithmId = integer(name = DatabaseConstant.ALGORITHM_ID).autoIncrement()
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)
    val algorithm = varchar(name = DatabaseConstant.ALGORITHM, length = 10)
    val created = long(name = DatabaseConstant.CREATED)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(algorithmId, name = DatabaseConstant.ALGORITHM_PK)

}
