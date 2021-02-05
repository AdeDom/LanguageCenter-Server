package com.lc.server.business.vocabulary

import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchVocabularyTranslationResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface VocabularyService {

    fun fetchVocabularyTranslation(): FetchVocabularyTranslationResponse

    fun addVocabularyTranslate(
        userId: String?,
        addVocabularyTranslationRequest: AddVocabularyTranslationRequest
    ): BaseResponse

}
