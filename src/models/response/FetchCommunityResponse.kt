package com.lc.server.models.response

import com.lc.server.models.model.Community

data class FetchCommunityResponse(
    var success: Boolean = false,
    var message: String? = null,
    var communities: List<Community> = emptyList(),
)
