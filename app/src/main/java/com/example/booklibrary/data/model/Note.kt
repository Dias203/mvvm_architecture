package com.example.booklibrary.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Parcelable là một interface trong Android giúp serialize (biến đổi) một đối tượng
// thành một chuỗi byte để "truyền dữ liệu giữa các thành phần trong ứng dụng (như Activity hoặc Fragment)."
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String,
    var isSelected: Boolean = false
):Serializable
