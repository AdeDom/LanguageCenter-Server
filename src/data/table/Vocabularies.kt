package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object Vocabularies : Table(name = DatabaseConstant.VOCABULARY_TABLE) {

    val vocabularyId = varchar(DatabaseConstant.VOCABULARY_ID, length = 50)
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)
    val vocabulary = varchar(DatabaseConstant.VOCABULARY, length = 20_000)
    val vocabularyGroupId = integer(DatabaseConstant.VOCABULARY_GROUP_ID).references(VocabularyGroups.vocabularyGroupId)
    val sourceLanguage = varchar(DatabaseConstant.SOURCE_LANGUAGE, length = 5)
    val reference = varchar(name = DatabaseConstant.REFERENCE, length = 20)
    val created = long(DatabaseConstant.CREATED)
    val updated = long(DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(vocabularyId, name = DatabaseConstant.VOCABULARY_PK)

}
