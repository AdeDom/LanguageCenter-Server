package com.lc.server.http

import com.lc.server.business.auth.AuthService
import com.lc.server.models.request.SignInRequest
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.authController(service: AuthService) {

    post<SignInRequest> {
        val request = call.receive<SignInRequest>()
        val response = service.signIn(request)
        call.respond(response)
    }

}
