package com.lc.server.models.response

import com.lc.server.models.model.Talk

data class FetchTalkUnreceivedResponse(
    var success: Boolean = false,
    var message: String? = null,
    var talks: List<Talk> = emptyList(),
)
