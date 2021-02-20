package com.lc.server.data.table

import com.lc.server.data.constant.DatabaseConstant
import org.jetbrains.exposed.sql.Table

object TranslationFeedbacks : Table(name = DatabaseConstant.TRANSLATION_FEEDBACK_TABLE) {

    val feedbackId = integer(name = DatabaseConstant.FEEDBACK_ID).autoIncrement()
    val userId = varchar(name = DatabaseConstant.USER_ID, length = 50).references(Users.userId)
    val translationId = integer(DatabaseConstant.TRANSLATION_ID).references(Translations.translationId)
    val isCorrect = bool(DatabaseConstant.IS_CORRECT).nullable()
    val created = long(name = DatabaseConstant.CREATED)
    val updated = long(name = DatabaseConstant.UPDATED).nullable()

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(feedbackId, name = DatabaseConstant.TRANSLATION_FEEDBACK_PK)

}
