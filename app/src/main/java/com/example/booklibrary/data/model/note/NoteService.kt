package com.example.booklibrary.data.model.note

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteService {

    @GET("/notes")
    suspend fun getNotes(): Response<Notes>

    @GET("/notes/{id}")
    suspend fun getNoteById(@Path("id") id: Int): Response<Note>

    @POST("/notes")
    suspend fun createNote(@Body note: Note): Response<Note>

    @PUT("/notes/{id}")
    suspend fun updateNote(@Path("id") id: Int, @Body note: Note): Response<Note>

    @DELETE("/notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int): Response<Unit>
}