package models.mockdata

import com.google.gson.annotations.SerializedName

data class RandomUserResponse(
    @SerializedName("info") val info: Info? = null,
    @SerializedName("results") val results: List<Result> = emptyList(),
)
