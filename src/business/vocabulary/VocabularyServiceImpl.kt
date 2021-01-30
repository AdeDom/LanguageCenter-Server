package com.lc.server.business.vocabulary

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.Translation
import com.lc.server.models.model.Vocabulary
import com.lc.server.models.request.AddVocabularyTranslation
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchVocabularyTranslationResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class VocabularyServiceImpl(
    private val repository: ServerRepository,
) : VocabularyService {

    override fun fetchVocabularyTranslation(): FetchVocabularyTranslationResponse {
        val response = FetchVocabularyTranslationResponse()

        val message: String = when {
            // validate Null Or Blank

            // validate values of variable

            // validate database

            // execute
            else -> {
                val vocabularyTranslationDb = repository.fetchVocabularyTranslation()

                // vocabulary list
                val vocabularies = vocabularyTranslationDb
                    .distinctBy { it.vocabularyId }
                    .map { db ->
                        // translation list
                        val translations = vocabularyTranslationDb
                            .filter { it.translationIdToVocabularyId == db.vocabularyId }
                            .map {
                                // map translation
                                Translation(
                                    translationId = it.translationId,
                                    translation = it.translation,
                                    targetLanguage = it.targetLanguage,
                                )
                            }

                        // map vocabulary
                        Vocabulary(
                            vocabularyId = db.vocabularyId,
                            vocabulary = db.vocabulary,
                            sourceLanguage = db.sourceLanguage,
                            created = db.created,
                            vocabularyGroupName = db.vocabularyGroupName,
                            translations = translations,
                        )
                    }

                response.vocabularies = vocabularies
                response.success = true
                "Fetch vocabulary translation success"
            }
        }

        response.message = message
        return response
    }

    override fun addVocabularyTranslate(addVocabularyTranslation: AddVocabularyTranslation): BaseResponse {
        val response = BaseResponse()
        val (vocabulary, source, target, translations) = addVocabularyTranslation

        val message: String = when {
            // validate Null Or Blank
            vocabulary.isNullOrBlank() -> "isNullOrBlank"
            source.isNullOrBlank() -> "isNullOrBlank"
            target.isNullOrBlank() -> "isNullOrBlank"
            translations.isNullOrEmpty() -> "isNullOrEmpty"

            // validate values of variable

            // validate database

            // execute
            else -> {
                response.success = repository.addVocabularyTranslate(addVocabularyTranslation)
                "Translate language success"
            }
        }

        response.message = message
        return response
    }

}
