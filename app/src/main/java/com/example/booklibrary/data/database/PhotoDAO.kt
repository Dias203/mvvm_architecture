package com.example.booklibrary.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.booklibrary.data.model.photo.PhotoItem

@Dao
interface PhotoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotoIfPhotoExists(photo: PhotoItem): Long

    @Update
    suspend fun updatePhoto(photo: PhotoItem)

    @Delete
    suspend fun deletePhoto(photo: PhotoItem)

    @Delete
    suspend fun deletePhotosSelected(photo: List<PhotoItem>)

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()

    /*@Query("SELECT * FROM photos ORDER BY id DESC")
    fun getAllPhotos(): LiveData<List<PhotoItem>>*/
    @Query("SELECT * FROM photos ORDER BY id ASC")
    fun getAllPhotosPaging(): PagingSource<Int, PhotoItem>

    @Query("SELECT * FROM photos WHERE author LIKE :query")
    fun searchPhoto(query: String?): LiveData<List<PhotoItem>>

    @Query("SELECT COUNT(*) FROM Photos")
    suspend fun getPhotosCount(): Int

    @Query("SELECT * FROM Photos WHERE id = :id")
    suspend fun getPhotoById(id: Int): PhotoItem?
}