package com.lc.server.http

import com.google.gson.Gson
import com.lc.server.business.chats.ChatsService
import com.lc.server.business.jwtconfig.JwtConfig
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.request.*
import com.lc.server.util.LanguageCenterConstant
import com.lc.server.util.userId
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach

@KtorExperimentalLocationsAPI
fun Route.chatsController(service: ChatsService) {

    post<SendMessageRequest> {
        val request = call.receive<SendMessageRequest>()
        val response = service.sendMessage(call.userId, request)
        call.respond(response)
    }

    get<ChatListUserInfoRequest> { request ->
        val response = service.chatListUserInfo(request)
        call.respond(response)
    }

    patch<ReadMessagesRequest> { request ->
        val response = service.readMessages(call.userId, request)
        call.respond(response)
    }

    patch<ReceiveMessageRequest> { request ->
        val response = service.receiveMessage(request)
        call.respond(response)
    }

    post<ResendMessageRequest> {
        val request = call.receive<ResendMessageRequest>()
        val response = service.resendMessage(request)
        call.respond(response)
    }

    get<FetchTalkUnreceivedRequest> {
        val response = service.fetchTalkUnreceived(call.userId)
        call.respond(response)
    }

}

@KtorExperimentalLocationsAPI
fun Route.chatsWebSocket(jwtConfig: JwtConfig) {

    val sendMessageSocket = mutableListOf<Pair<WebSocketSession, String>>()
    webSocket("/ws/chats/send-message") {
        println("chatsWebSocket : begin")
        val accessToken: String = call.request.header(LanguageCenterConstant.ACCESS_TOKEN)!!
        val userId = jwtConfig.decodeJwtGetUserId(accessToken)

        sendMessageSocket.add(Pair(this, userId))

        try {
            println("chatsWebSocket : 1")
            incoming
                .consumeAsFlow()
                .onEach { frame ->
                    val text = (frame as Frame.Text).readText()
                    val talk = Gson().fromJson(text, TalkSendMessageWebSocket::class.java)

                    println("chatsWebSocket : 2 $text")
                    sendMessageSocket
                        .filter { it.second == talk.toUserId }
                        .forEach { pair ->
                            try {
                                pair.first.send(Frame.Text(text).copy())
                            } catch (t: Throwable) {
                                try {
                                    pair.first.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, ""))
                                } catch (ignore: ClosedSendChannelException) {
                                }
                            }
                        }
                    println("chatsWebSocket : 3")
                }
                .catch {
                    println("chatsWebSocket : catch 4 ${it.message}")
                    it.printStackTrace()
                }
                .collect()
            println("chatsWebSocket : 5")
        } catch (e: ClosedReceiveChannelException) {
            println("chatsWebSocket : 6 ${e.message}")
            println("chatsWebSocket : catch onClose ${closeReason.await()}")
            e.printStackTrace()
        } catch (e: Throwable) {
            println("chatsWebSocket : 7 ${e.message}")
            println("chatsWebSocket : catch onError ${closeReason.await()}")
            e.printStackTrace()
        } finally {
            sendMessageSocket.add(Pair(this, userId))
            close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
        }
        println("chatsWebSocket : end")
    }

}
