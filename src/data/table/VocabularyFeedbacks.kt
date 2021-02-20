package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object VocabularyFeedbacks : Table(name = DatabaseConstant.VOCABULARY_FEEDBACK_TABLE) {

    val feedbackId = integer(name = DatabaseConstant.FEEDBACK_ID).autoIncrement()
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)
    val vocabularyId = varchar(DatabaseConstant.VOCABULARY_ID, length = 50).references(Vocabularies.vocabularyId)
    val rating = float(DatabaseConstant.RATING)
    val created = long(name = DatabaseConstant.CREATED)
    val updated = long(name = DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(feedbackId, name = DatabaseConstant.VOCABULARY_FEEDBACK_PK)

}
