package com.lx.data


import com.google.gson.annotations.SerializedName

data class MemberRequestResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String
) {
    data class Data(
        @SerializedName("assign_content")
        val assignContent: String,
        @SerializedName("assign_title")
        val assignTitle: String,
        @SerializedName("assign_write_request_no")
        val awrn: Int,
        @SerializedName("dog_no")
        val dogNo: Int,
        @SerializedName("end_time")
        val endTime: String,
        @SerializedName("member_address")
        val memberAddress: String,
        @SerializedName("member_name")
        val memberName: String,
        @SerializedName("member_no")
        val memberNo: Int,
        @SerializedName("start_time")
        val startTime: String,
        @SerializedName("write_time")
        val writeTime: String,
        @SerializedName("writeX")
        val writeX: Double,
        @SerializedName("writeY")
        val writeY: Double
    )
}