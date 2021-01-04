package models.mockdata

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: Int? = null,
    @SerializedName("seed") val seed: String? = null,
    @SerializedName("version") val version: String? = null,
)
