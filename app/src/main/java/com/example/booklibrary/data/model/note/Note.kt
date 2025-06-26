package com.example.booklibrary.data.model.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("noteTitle")
    val noteTitle: String,

    @SerializedName("noteBody")
    val noteBody: String,

    @SerializedName("isSelected")
    var isSelected: Boolean = false,

    // Thêm field để theo dõi trạng thái đồng bộ
    val isSynced: Boolean = false,
    val isLocalOnly: Boolean = false // True nếu note chưa được tạo trên server
): Serializable



class Notes : ArrayList<Note>() {

}
