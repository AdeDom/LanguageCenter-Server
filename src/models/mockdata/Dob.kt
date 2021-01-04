package models.mockdata

import com.google.gson.annotations.SerializedName

data class Dob(
    @SerializedName("age") val age: Int? = null,
    @SerializedName("date") val date: String? = null,
)
