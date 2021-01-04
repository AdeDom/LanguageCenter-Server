package models.mockdata

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city") val city: String? = null,
    @SerializedName("coordinates") val coordinates: Coordinates? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("postcode") val postcode: Int? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("street") val street: Street? = null,
    @SerializedName("timezone") val timezone: Timezone? = null,
)
