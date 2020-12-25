package com.lc.server.models.response

import com.lc.server.models.model.Token

data class SignInResponse(
    var success: Boolean = false,
    var message: String? = null,
    var token: Token? = null,
    var isUpdateProfile: Boolean = false,
)
