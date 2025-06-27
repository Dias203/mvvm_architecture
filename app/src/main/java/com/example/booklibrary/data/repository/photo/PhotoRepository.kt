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

    suspend fun insertPhoto(photo: PhotoItem) = withContext(Dispatchers.IO) {
        db.getPhotoDao().insertPhoto(photo)
    }

    suspend fun deletePhoto(photo: PhotoItem) = withContext(Dispatchers.IO) {
        db.getPhotoDao().deletePhoto(photo)
    }

    suspend fun deletePhotosSelected(photos: List<PhotoItem>) = withContext(Dispatchers.IO) {
        db.getPhotoDao().deletePhotosSelected(photos)
    }

    suspend fun deleteAllPhotos() = withContext(Dispatchers.IO) {
        db.getPhotoDao().deleteAllPhotos()
    }

    suspend fun updatePhoto(photo: PhotoItem) = withContext(Dispatchers.IO) {
        ECOLog.showLog("Updating note: $photo")
        db.getPhotoDao().updatePhoto(photo)
    }

    //fun getAllPhoto() = db.getPhotoDao().getAllPhotos()
    fun getAllPhotosPaging() = db.getPhotoDao().getAllPhotosPaging()
    fun searchPhoto(query: String) = db.getPhotoDao().searchPhoto(query)

    private suspend fun isEmpty(): Boolean {
        return db.getPhotoDao().getPhotosCount() == 0
    }

    suspend fun syncPhotosFromAPI(): Result<Unit> = withContext(Dispatchers.IO) {
        val result = db.getPhotoDao().getPhotosCount()
        ECOLog.showLog("Come here - result: $result")

        if (isEmpty()) {
            ECOLog.showLog("Come here - 1")
            val response = photoService.getPhotos(page = 1, limit = 15)

            return@withContext if (response.isSuccessful) {
                response.body()?.let { photos ->
                    photos.forEach { photoItem ->
                        db.getPhotoDao().insertPhoto(photoItem)
                    }
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("API call failed: ${response.code()}"))
            }
        } else {
            ECOLog.showLog("Come here - 2")
            return@withContext Result.success(Unit)
        }
    }

}