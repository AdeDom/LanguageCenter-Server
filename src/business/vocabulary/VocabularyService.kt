package com.lc.server.business.vocabulary

import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchVocabularyGroupResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface VocabularyService {

    fun addVocabularyTranslate(
        userId: String?,
        addVocabularyTranslationRequest: AddVocabularyTranslationRequest
    ): BaseResponse

    fun fetchVocabularyGroup(): FetchVocabularyGroupResponse

}
