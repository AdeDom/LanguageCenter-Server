package models.response

import com.google.gson.annotations.SerializedName

data class GoogleApiUserInfoResponse(
    @SerializedName("email") val email: String? = null,
    @SerializedName("family_name") val familyName: String? = null,
    @SerializedName("given_name") val givenName: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("locale") val locale: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("picture") val picture: String? = null,
    @SerializedName("verified_email") val verifiedEmail: Boolean = false,
)
