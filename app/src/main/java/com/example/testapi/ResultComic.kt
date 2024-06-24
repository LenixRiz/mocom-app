package com.example.testapi

import com.google.gson.annotations.SerializedName

class ResultComic (
    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("comic")
    val comic: List<DataItem>? = null,

    @field:SerializedName("status")
    val status: Int? = null
)