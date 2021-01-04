package models.mockdata

import com.google.gson.annotations.SerializedName

data class Street(
    @SerializedName("name") val name: String? = null,
    @SerializedName("number") val number: Int? = null,
)
