package com.lc.server.business.vocabulary

import com.lc.server.business.business.ServerBusiness
import com.lc.server.data.repository.ServerRepository
import com.lc.server.models.model.*
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.request.FetchVocabularyDetailRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchVocabularyDetailResponse
import com.lc.server.models.response.FetchVocabularyGroupResponse
import com.lc.server.util.LanguageCenterConstant
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
internal class VocabularyServiceImpl(
    private val repository: ServerRepository,
    private val business: ServerBusiness,
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

    override fun fetchVocabularyDetail(
        userId: String?,
        fetchVocabularyDetailRequest: FetchVocabularyDetailRequest
    ): FetchVocabularyDetailResponse {
        val response = FetchVocabularyDetailResponse()
        val (_, vocabularyGroupId) = fetchVocabularyDetailRequest

        val message: String = when {
            // validate Null Or Blank
            userId.isNullOrBlank() -> "isNullOrBlank"
            vocabularyGroupId == null -> "Null"

            // validate values of variable

            // validate database

            // execute
            else -> {
                val vocabularyDetailDb = repository.fetchVocabularyDetail(fetchVocabularyDetailRequest)
                val users = repository.getCommunityUsers(userId)
                val userLocales = repository.getCommunityUserLocales()

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

                        // user info
                        val user = users.singleOrNull { it.userId == db.userId }
                        val userInfo = Community(
                            userId = user?.userId,
                            email = user?.email,
                            givenName = user?.givenName?.capitalize(),
                            familyName = user?.familyName?.capitalize(),
                            name = user?.name?.capitalize(),
                            picture = user?.picture,
                            gender = user?.gender,
                            age = user?.birthDate?.let { business.getAgeInt(it) },
                            birthDateString = business.convertDateTimeLongToString(user?.birthDate),
                            birthDateLong = user?.birthDate,
                            verifiedEmail = user?.verifiedEmail,
                            aboutMe = user?.aboutMe,
                            created = business.convertDateTimeLongToString(user?.created),
                            updated = business.convertDateTimeLongToString(user?.updated),
                            localNatives = userLocales.filter { it.userId == user?.userId }
                                .filter { it.localeType == LanguageCenterConstant.LOCALE_NATIVE }
                                .map { UserInfoLocale(locale = it.locale, level = it.level) },
                            localLearnings = userLocales.filter { it.userId == user?.userId }
                                .filter { it.localeType == LanguageCenterConstant.LOCALE_LEARNING }
                                .map { UserInfoLocale(locale = it.locale, level = it.level) },
                        )

                        // map vocabulary
                        Vocabulary(
                            vocabularyId = db.vocabularyId,
                            userInfo = if (db.userId == userId) null else userInfo,
                            vocabulary = db.vocabulary.capitalize(),
                            sourceLanguage = db.sourceLanguage,
                            reference = db.reference,
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
