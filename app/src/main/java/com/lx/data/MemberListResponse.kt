package com.lx.data


import com.google.gson.annotations.SerializedName

data class MemberListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("memberAddress")
        val memberAddress: String,
        @SerializedName("memberId")
        val memberId: String,
        @SerializedName("memberImage")
        val memberImage: String,
        @SerializedName("memberName")
        val memberName: String,
        @SerializedName("memberPw")
        val memberPw: String
    )
}