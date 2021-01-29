package com.lc.server.business.vocabulary

import com.lc.server.models.request.AddVocabularyTranslation
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchVocabularyTranslationResponse
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
interface VocabularyService {

    fun fetchVocabularyTranslation(): FetchVocabularyTranslationResponse

    fun addVocabularyTranslate(addVocabularyTranslation: AddVocabularyTranslation): BaseResponse

}
