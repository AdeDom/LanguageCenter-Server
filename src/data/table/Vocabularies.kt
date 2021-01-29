package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object Vocabularies : Table(name = DatabaseConstant.VOCABULARY_TABLE) {

    val vocabularyId = varchar(DatabaseConstant.VOCABULARY_ID, length = 50)
    val vocabulary = varchar(DatabaseConstant.VOCABULARY, length = 2_000)
    val vocabularyGroupId = integer(DatabaseConstant.VOCABULARY_GROUP_ID).references(VocabularyGroups.vocabularyGroupId)
    val sourceLanguage = varchar(DatabaseConstant.SOURCE_LANGUAGE, length = 5)
    val created = long(DatabaseConstant.CREATED)
    val updated = long(DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(vocabularyId, name = DatabaseConstant.VOCABULARY_PK)

}
