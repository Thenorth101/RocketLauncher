package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiRocket(
    @SerializedName("rocket_name")
    val name: String,

    @SerializedName("flickr_images")
    val imageList: List<String>,

    @SerializedName("country")
    val country: String,

    @SerializedName("engines")
    val engine: ApiEngine
)

data class ApiEngine(
    @SerializedName("number")
    val number: Short
)

