package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object Translations : Table(name = DatabaseConstant.TRANSLATION_TABLE) {

    val translationId = integer(DatabaseConstant.TRANSLATION_ID).autoIncrement()
    val vocabularyId = varchar(DatabaseConstant.VOCABULARY_ID, length = 50).references(Vocabularies.vocabularyId)
    val translation = varchar(DatabaseConstant.TRANSLATION, length = 20_000).nullable()
    val targetLanguage = varchar(DatabaseConstant.TARGET_LANGUAGE, length = 5)
    val created = long(DatabaseConstant.CREATED)
    val updated = long(DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(translationId, name = DatabaseConstant.TRANSLATION_PK)

}
