package com.lc.server.business.vocabulary

import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.Translation
import com.lc.server.models.model.Vocabulary
import com.lc.server.models.model.VocabularyGroup
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.request.FetchVocabularyDetailRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchVocabularyDetailResponse
import com.lc.server.models.response.FetchVocabularyGroupResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class VocabularyServiceImpl(
    private val repository: ServerRepository,
) : VocabularyService {

    override fun addVocabularyTranslate(
        userId: String?,
        addVocabularyTranslationRequest: AddVocabularyTranslationRequest
    ): BaseResponse {
        val response = BaseResponse()
        val (vocabulary, source, target, translations, reference) = addVocabularyTranslationRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"
            vocabulary.isNullOrBlank() -> "isNullOrBlank"
            source.isNullOrBlank() -> "isNullOrBlank"
            target.isNullOrBlank() -> "isNullOrBlank"
            translations.isNullOrEmpty() -> "isNullOrEmpty"
            reference.isNullOrEmpty() -> "isNullOrEmpty"

            // validate values of variable

            // validate database
            repository.isValidateVocabulary(vocabulary) -> "Already have this vocabulary!!!"

            // execute
            else -> {
                response.success = repository.addVocabularyTranslate(userId, addVocabularyTranslationRequest)
                "Translate language success"
            }
        }

        response.message = message
        return response
    }

    override fun fetchVocabularyGroup(): FetchVocabularyGroupResponse {
        val response = FetchVocabularyGroupResponse()

        val message: String = when {
            // validate Null Or Blank

            // validate values of variable

            // validate database

            // execute
            else -> {
                val vocabularyGroups = repository.fetchVocabularyGroup().map {
                    VocabularyGroup(
                        vocabularyGroupId = it.vocabularyGroupId,
                        vocabularyGroupName = it.vocabularyGroupName,
                        created = it.created,
                        updated = it.updated,
                        isShow = it.isShow,
                    )
                }
                response.vocabularyGroups = vocabularyGroups
                response.success = true
                "Fetch vocabulary group success"
            }
        }

        response.message = message
        return response
    }

    override fun fetchVocabularyDetail(fetchVocabularyDetailRequest: FetchVocabularyDetailRequest): FetchVocabularyDetailResponse {
        val response = FetchVocabularyDetailResponse()
        val (_, vocabularyGroupId) = fetchVocabularyDetailRequest

        val message: String = when {
            // validate Null Or Blank
            vocabularyGroupId == null -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val vocabularyDetailDb = repository.fetchVocabularyDetail(fetchVocabularyDetailRequest)

                // vocabulary
                val vocabularies = vocabularyDetailDb
                    .distinctBy { it.vocabularyId }
                    .map { db ->
                        // translation list
                        val translations = vocabularyDetailDb
                            .filter { it.translationIdToVocabularyId == db.vocabularyId }
                            .map {
                                // map translation
                                Translation(
                                    translationId = it.translationId,
                                    translation = it.translation?.capitalize(),
                                    targetLanguage = it.targetLanguage,
                                )
                            }

                        // map vocabulary
                        Vocabulary(
                            vocabularyId = db.vocabularyId,
                            vocabulary = db.vocabulary.capitalize(),
                            sourceLanguage = db.sourceLanguage,
                            created = db.created,
                            vocabularyGroupName = db.vocabularyGroupName,
                            translations = translations,
                        )
                    }

                response.vocabularies = vocabularies
                response.success = true
                "Fetch vocabulary detail success"
            }
        }

        response.message = message
        return response
    }

}
