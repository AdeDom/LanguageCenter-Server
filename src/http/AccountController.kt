package com.lc.server.http

import com.lc.server.business.account.AccountService
import com.lc.server.models.request.*
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

    get<OtherUserInfoRequest> {
        val otherUserId = call.parameters["otherUserId"]
        val response = service.fetchUserInfo(otherUserId)
        call.respond(response)
    }

    put<GuideUpdateProfileRequest> {
        val request = call.receive<GuideUpdateProfileRequest>()
        val response = service.guideUpdateProfile(call.userId, request)
        call.respond(response)
    }

    put<EditProfileRequest> {
        val request = call.receive<EditProfileRequest>()
        val response = service.editProfile(call.userId, request)
        call.respond(response)
    }

    put<EditLocaleRequest> {
        val request = call.receive<EditLocaleRequest>()
        val response = service.editLocale(call.userId, request)
        call.respond(response)
    }

    get<FetchFriendInfoRequest> {
        val response = service.fetchFriendInfo(call.userId)
        call.respond(response)
    }

}
