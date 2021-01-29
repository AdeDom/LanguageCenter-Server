package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object VocabularyGroups : Table(name = DatabaseConstant.VOCABULARY_GROUP_TABLE) {

    val vocabularyGroupId = integer(DatabaseConstant.VOCABULARY_GROUP_ID).autoIncrement()
    val vocabularyGroupName = varchar(DatabaseConstant.VOCABULARY_GROUP_NAME, length = 100)
    val created = long(DatabaseConstant.CREATED)
    val updated = long(DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(vocabularyGroupId, name = DatabaseConstant.VOCABULARY_GROUP_PK)

}
