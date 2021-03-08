package bean

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("business_status") val business_status: String,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("icon") val icon: String,
    @SerializedName("name") val name: String,
    @SerializedName("opening_hours") val opening_hours: OpeningHours,
    @SerializedName("permanently_closed") val permanently_closed: Boolean,
    @SerializedName("photos") val photos: List<Photo>,
    @SerializedName("place_id") val place_id: String,
    @SerializedName("plus_code") val plus_code: PlusCode,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reference") val reference: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("types") val types: List<String>,
    @SerializedName("user_ratings_total") val user_ratings_total: Int,
    @SerializedName("vicinity") val vicinity: String
)