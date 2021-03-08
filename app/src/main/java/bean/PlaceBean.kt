package bean

import com.google.gson.annotations.SerializedName

data class PlaceBean(
    @SerializedName ("html_attributions") val html_attributions: List<Any>,
    @SerializedName("next_page_token") val next_page_token: String,
    @SerializedName("results")val results: List<Result>,
    @SerializedName("status")val status: String
)