package models.response

import com.google.gson.annotations.SerializedName

data class GoogleApiTokenResponse(
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("id_token") val idToken: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("scope") val scope: String? = null,
    @SerializedName("token_type") val tokenType: String? = null,
)
