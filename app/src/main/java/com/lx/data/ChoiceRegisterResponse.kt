package com.lx.data


import com.google.gson.annotations.SerializedName

data class ChoiceRegisterResponse(
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
        @SerializedName("acrn")
        val acrn: Int,
        @SerializedName("assignContent")
        val assignContent: String,
        @SerializedName("assignTitle")
        val assignTitle: String,
        @SerializedName("careNo")
        val careNo: Int,
        @SerializedName("dogNo")
        val dogNo: Int,
        @SerializedName("endTime")
        val endTime: String,
        @SerializedName("memberName")
        val memberName: String,
        @SerializedName("memberNo")
        val memberNo: Int,
        @SerializedName("startTime")
        val startTime: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}