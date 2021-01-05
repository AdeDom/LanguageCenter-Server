package com.lc.server.models.request

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/community/add-algorithm")
data class AddAlgorithmRequest(
    val algorithm: String? = null,
)
