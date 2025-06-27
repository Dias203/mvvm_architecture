package com.example.booklibrary.data.repository.photo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.model.photo.PhotoItem
import kotlinx.coroutines.delay
import retrofit2.HttpException

class PhotoPagingSource(private val photoService: PhotoService) : PagingSource<Int, PhotoItem>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        try {
            val nextPage = params.key ?: 1
            val pageSize = params.loadSize
            val response = photoService.getPhotos(nextPage, pageSize)

            if(response.isSuccessful) {
                val photos = response.body() ?: emptyList()

                val prevKey = if (nextPage == 1) null else nextPage - 1
                val nextKey = if (photos.isEmpty() || photos.size < pageSize) null else nextPage + 1

                return LoadResult.Page(
                    data = photos,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            else {
                return LoadResult.Error(HttpException(response))
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}