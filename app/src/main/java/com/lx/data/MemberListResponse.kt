package com.lx.data


import com.google.gson.annotations.SerializedName

data class MemberListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("header")
    val header: Header
) {

    data class Data(
        @SerializedName("member_id")
        val memberId: String,
        @SerializedName("member_name")
        val memberName: String,
        @SerializedName("member_pw")
        val memberPw: String,
    )
    data class Header(
        @SerializedName("total")
        val total: Int
    )
}