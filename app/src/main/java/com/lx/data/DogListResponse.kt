package com.lx.data


import com.google.gson.annotations.SerializedName

data class DogListResponse(
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
        val dogVaccination: String,
        @SerializedName("dogAge")
        val dogAge: String,
        @SerializedName("dogCharacter")
        val dogCharacter: String,
        @SerializedName("dogEducation")
        val dogEducation: String,
        @SerializedName("dogGender")
        val dogBreed: String,
        @SerializedName("dogBreed")
        val dogGender: String,
        @SerializedName("dogImage")
        val dogImage: String,
        @SerializedName("dogName")
        val dogName: String,
        @SerializedName("dogNo")
        val dogNo: String,
        @SerializedName("memberNo")
        val memberNo: String
    )

    data class Header(
        @SerializedName("total")
        val total: Int
    )
}