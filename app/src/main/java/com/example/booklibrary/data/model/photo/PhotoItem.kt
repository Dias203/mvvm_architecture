package com.example.booklibrary.data.model.photo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "photos")
data class PhotoItem(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,

    @SerializedName("albumId")
    val albumId: Int,

    @SerializedName("author")
    val author: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("download_url")
    val downloadUrl: String
) : Serializable

class Photos : ArrayList<PhotoItem>() {}
