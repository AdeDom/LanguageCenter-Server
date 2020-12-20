package com.lc.server.http

import com.lc.server.business.account.AccountService
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.UserInfoRequest
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.accountController(service: AccountService) {

    get<UserInfoRequest> {
        val response = service.fetchUserInfo(call.userId)
        call.respond(response)
    }

    put<EditProfileRequest> {
        val request = call.receive<EditProfileRequest>()
        val response = service.editProfile(call.userId, request)
        call.respond(response)
    }

}
