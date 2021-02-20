package com.lc.server.http

import com.lc.server.business.vocabulary.VocabularyService
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.request.FetchVocabularyDetailRequest
import com.lc.server.models.request.FetchVocabularyGroupRequest
import com.lc.server.models.request.VocabularyTranslationFeedbackRequest
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.vocabularyController(service: VocabularyService) {

    post<AddVocabularyTranslationRequest> {
        val request = call.receive<AddVocabularyTranslationRequest>()
        val response = service.addVocabularyTranslate(call.userId, request)
        call.respond(response)
    }

    get<FetchVocabularyGroupRequest> {
        val response = service.fetchVocabularyGroup()
        call.respond(response)
    }

    get<FetchVocabularyDetailRequest> { request ->
        val response = service.fetchVocabularyDetail(call.userId, request)
        call.respond(response)
    }

    post<VocabularyTranslationFeedbackRequest> {
        val request = call.receive<VocabularyTranslationFeedbackRequest>()
        val response = service.vocabularyTranslationFeedback(call.userId, request)
        call.respond(response)
    }

}
