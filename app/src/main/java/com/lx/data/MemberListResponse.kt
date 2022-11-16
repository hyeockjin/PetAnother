package com.lx.data


import com.google.gson.annotations.SerializedName

data class MemberListResponse(
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
        @SerializedName("member_address")
        val memberAddress: String,
        @SerializedName("member_id")
        val memberId: String,
        @SerializedName("member_image")
        val memberImage: String,
        @SerializedName("member_name")
        val memberName: String,
        @SerializedName("member_pw")
        val memberPw: String,
        @SerializedName("member_pw")
        val memberNo: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}