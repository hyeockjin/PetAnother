package com.lx.data


import com.google.gson.annotations.SerializedName

data class CareListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("header")
    val header: Header,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("care_address")
        val careAddress: String,
        @SerializedName("care_approval")
        val careApproval: Int,
        @SerializedName("care_education")
        val careEducation: Int,
        @SerializedName("care_experience")
        val careExperience: String,
        @SerializedName("care_id")
        val careId: String,
        @SerializedName("care_image")
        val careImage: String,
        @SerializedName("care_name")
        val careName: String,
        @SerializedName("care_no")
        val careNo: Int,
        @SerializedName("care_pw")
        val carePw: String,
        @SerializedName("careX")
        val careX: Double,
        @SerializedName("careY")
        val careY: Double
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}