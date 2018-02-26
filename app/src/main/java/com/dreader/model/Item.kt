package com.dreader.model

import com.google.gson.annotations.SerializedName

data class Item(
        val id: Long,
        val title: String,
        @SerializedName("comments_count")
        val commentsCount: String,
        @SerializedName("file_type")
        val fileType: String,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("gif_url")
        val gifUrl: String,
        @SerializedName("inner_image_width")
        val gifWidth: Int,
        @SerializedName("inner_image_height")
        val gifHeight: Int
) {

        companion object {
                val TYPE_IMAGE = "image"
                val TYPE_GIF = "image_gif"
        }

        val isGif: Boolean
                get() = fileType == TYPE_GIF

        fun hasValidType() = fileType == TYPE_IMAGE || fileType == TYPE_GIF
}