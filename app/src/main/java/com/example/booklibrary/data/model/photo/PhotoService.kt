package com.example.booklibrary.data.model.photo

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PhotoService {

    @GET("/v2/list")
    suspend fun getPhotos(): Response<Photos>

    @GET("/v2/list/{id}")
    suspend fun getPhotoById(@Path("id") id: Int): Response<PhotoItem>

    @POST("/v2/list")
    suspend fun createPhoto(@Body photo: PhotoItem): Response<PhotoItem>

    @PUT("/v2/list/{id}")
    suspend fun updatePhoto(@Path("id") id: Int, @Body photo: PhotoItem): Response<PhotoItem>

    @DELETE("/v2/list/{id}")
    suspend fun deletePhoto(@Path("id") id: Int): Response<Unit>
}