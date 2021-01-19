package com.lc.server.http

import com.google.gson.Gson
import com.lc.server.business.chats.ChatsService
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.util.LanguageCenterConstant
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

@KtorExperimentalLocationsAPI
fun Route.chatsController(service: ChatsService) {

    post<SendMessageRequest> {
        val request = call.receive<SendMessageRequest>()
        val response = service.sendMessage(call.userId, request)
        call.respond(response)
    }

}

@KtorExperimentalLocationsAPI
fun Route.chatsWebSocket(service: ChatsService, jwtConfig: JwtConfig) {

    val sendMessageSocket = mutableListOf<Pair<WebSocketSession, String>>()
    webSocket("/ws/chats/send-message") {
        val accessToken: String = call.request.header(LanguageCenterConstant.ACCESS_TOKEN)!!
        val userId = jwtConfig.decodeJwtGetUserId(accessToken)

        sendMessageSocket.add(Pair(this, userId))

        try {
            incoming
                .consumeAsFlow()
                .onEach { frame ->
                    val text = (frame as Frame.Text).readText()
                    val fromJson = Gson().fromJson(text, TalkSendMessageWebSocket::class.java)
                    val currentTimeMillis = System.currentTimeMillis()
                    val dateSdf = SimpleDateFormat("E, MMM d", Locale("th", "TH"))
                    val timeSdf = SimpleDateFormat("HH:mm", Locale("th", "TH"))
                    val talk = fromJson.copy(
                        fromUserId = userId,
                        dateString = dateSdf.format(currentTimeMillis),
                        timeString = timeSdf.format(currentTimeMillis),
                        dateTimeLong = currentTimeMillis,
                    )

                    val result = service.sendMessage(talk)
                    if (result) {
                        val toJson = Gson().toJson(talk)
                        sendMessageSocket
                            .filter { it.second == talk.fromUserId || it.second == talk.toUserId }
                            .forEach { pair ->
                                pair.first.send(Frame.Text(toJson))
                            }
                    }
                }
                .catch { }
                .collect()
        } finally {
            sendMessageSocket.add(Pair(this, userId))
        }
    }

}
