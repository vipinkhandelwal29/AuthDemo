package com.example.place.bean

import com.google.gson.annotations.SerializedName

data class PlaceSerializedBean(
    @SerializedName("place_bean") val place_bean: ArrayList<PlaceBean>)

