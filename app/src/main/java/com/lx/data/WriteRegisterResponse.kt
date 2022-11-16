package com.lx.data


import com.google.gson.annotations.SerializedName

data class WriteRegisterResponse(
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
        @SerializedName("awrn")
        val awrn: Int,
        @SerializedName("memberNo")
        val memberNo: String,
        @SerializedName("dogNo")
        val dogNo: String,
        @SerializedName("memberName")
        val memberName: String,
        @SerializedName("dogName")
        val dogName: String,
        @SerializedName("startTime")
        val startTime: String,
        @SerializedName("endTime")
        val endTime: String,
        @SerializedName("assignContent")
        val assignContent: String,
        @SerializedName("assignTitle")
        val assignTitle: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}