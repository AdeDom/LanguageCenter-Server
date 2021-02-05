package com.lc.server.http

import com.lc.server.business.vocabulary.VocabularyService
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.request.FetchVocabularyTranslationRequest
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.vocabularyController(service: VocabularyService) {

    get<FetchVocabularyTranslationRequest> {
        val response = service.fetchVocabularyTranslation()
        call.respond(response)
    }

    post<AddVocabularyTranslationRequest> {
        val request = call.receive<AddVocabularyTranslationRequest>()
        val response = service.addVocabularyTranslate(call.userId, request)
        call.respond(response)
    }

}
