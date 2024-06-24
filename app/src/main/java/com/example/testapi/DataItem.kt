package com.example.testapi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataItem : Serializable {
    @field:SerializedName("comic_title")
    val comicTitle: String? = null

    @field:SerializedName("comic_id")
    val comicId: String? = null

    @field:SerializedName("comic_author")
    val comicAuthor: String? = null

    @field:SerializedName("comic_image")
    val comicImage: String? = null

    @field:SerializedName("comic_description")
    val comicDescription: String? = null
}