package com.lx.data


import com.google.gson.annotations.SerializedName

data class StudentListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("output")
    val output: Output
) {
    data class Output(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("header")
        val header: Header
    ) {
        data class Data(
            @SerializedName("age")
            val age: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("mobile")
            val mobile: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("filepath")
            val filepath: String
        )

        data class Header(
            @SerializedName("total")
            val total: Int
        )
    }
}