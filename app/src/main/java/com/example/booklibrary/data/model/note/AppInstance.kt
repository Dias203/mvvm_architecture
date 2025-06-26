package com.example.booklibrary.data.model.note

import com.example.booklibrary.data.model.photo.PhotoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppInstance {
    companion object {
        private const val BASE_URL = "https://picsum.photos"

        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val noteService: NoteService by lazy {
            retrofit.create(NoteService::class.java)
        }

        val photoService: PhotoService by lazy {
            retrofit.create(PhotoService::class.java)
        }
    }
}