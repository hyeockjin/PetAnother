package com.lx.data


import com.google.gson.annotations.SerializedName

data class CareListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {


    data class Data(
        @SerializedName("careAddress")
        val careAddress: String,
        @SerializedName("careApproval")
        val careApproval: Int,
        @SerializedName("careEducation")
        val careEducation: Int,
        @SerializedName("careExperience")
        val careExperience: Int,
        @SerializedName("careId")
        val careId: String,
        @SerializedName("careImage")
        val careImage: Any?,
        @SerializedName("careN")
        val careN: String,
        @SerializedName("careNickname")
        val careNickname: String,
        @SerializedName("careNo")
        val careNo: Int,
        @SerializedName("carePw")
        val carePw: String,
        @SerializedName("careX")
        val careX: Double,
        @SerializedName("careY")
        val careY: Double
    )

}