package com.example.booklibrary.data.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteInstance {
    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val noteService: NoteService by lazy {
            retrofit.create(NoteService::class.java)
        }
    }
}