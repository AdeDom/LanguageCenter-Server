package com.lc.server.http

import com.lc.server.business.vocabulary.VocabularyService
import com.lc.server.models.request.AddVocabularyTranslation
import com.lc.server.models.request.FetchVocabularyTranslationRequest
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

    post<AddVocabularyTranslation> {
        val request = call.receive<AddVocabularyTranslation>()
        val response = service.addVocabularyTranslate(request)
        call.respond(response)
    }

}
