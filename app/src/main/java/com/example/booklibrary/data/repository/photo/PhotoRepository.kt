package com.example.booklibrary.data.repository.photo

import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.database.AppDatabase
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRepository(
    private val db: AppDatabase,
    private val photoService: PhotoService
) {
    suspend fun insertPhotos(photos: List<PhotoItem>) = withContext(Dispatchers.IO) {
        db.getPhotoDao().insertPhotos(photos)
    }
    fun getAllPhotos() = db.getPhotoDao().getAllPhotosOnce()
}