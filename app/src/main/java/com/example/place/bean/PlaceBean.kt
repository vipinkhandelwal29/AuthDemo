package com.example.place.bean

import com.google.gson.annotations.SerializedName

data class PlaceBean(
    @SerializedName ("html_attributions") val html_attributions: ArrayList<Any>,
    @SerializedName("next_page_token") val next_page_token: String,
    @SerializedName("results")val results: ArrayList<Result>,
    @SerializedName("status")val status: String
)