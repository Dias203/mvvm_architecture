package com.example.booklibrary.ui.screens.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.repository.photo.PhotoRepository
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel(
    private val photoRepository: PhotoRepository,
    private val photoService: PhotoService,
) : ViewModel() {

    var listener: PhotoListener? = null

    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    private var currentPage = 1
    private val pageSize = 20
    var isLoading = false
    var isLastPage = false

    private val loadedPhotos = mutableListOf<PhotoItem>()

    fun loadInitialPhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        listener?.isLoading()
        currentPage = 1
        isLastPage = false
        loadedPhotos.clear()
        loadPhotos(onLoaded)
    }

    private fun loadPhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        isLoading = true
        viewModelScope.launch {
            try {
                val response = photoService.getPhotos(currentPage, pageSize)
                if (response.isSuccessful) {
                    val newData = response.body().orEmpty()
                    loadedPhotos.addAll(newData)
                    _photos.postValue(loadedPhotos)
                    onLoaded(newData)
                    // Kiểm tra nếu ít hơn pageSize thì là trang cuối
                    if (newData.size < pageSize) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                }
            } catch (e: Exception) {
                listener?.isLoadFail()
                ECOLog.showLog("Exception: $e")
            } finally {
                isLoading = false
                listener?.isLoaded()
            }
        }
    }

    fun loadMorePhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        if (isLoading || isLastPage) return
        loadPhotos(onLoaded)
    }
}