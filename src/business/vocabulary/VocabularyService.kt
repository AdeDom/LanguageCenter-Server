package com.lc.server.business.vocabulary

import com.lc.server.models.response.FetchVocabularyTranslationResponse

interface VocabularyService {

    fun fetchVocabularyTranslation(): FetchVocabularyTranslationResponse

}
