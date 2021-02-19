package com.lc.server.http

import com.lc.server.business.auth.AuthService
import com.lc.server.models.request.RefreshTokenRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.request.ValidateTokenRequest
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

    post<RefreshTokenRequest> {
        val request = call.receive<RefreshTokenRequest>()
        val response = service.refreshToken(request)
        call.respond(response.first, response.second)
    }

    get<ValidateTokenRequest> { request ->
        val response = service.validateToken(request)
        call.respond(response)
    }

}
