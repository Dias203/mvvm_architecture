package com.example.booklibrary.ui.screens.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.repository.photo.PhotoRepository
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val photoRepository: PhotoRepository,
    private val photoService: PhotoService,
) : ViewModel() {

    var listener: PhotoListener? = null
    private val _syncResult = MutableLiveData<Result<Unit>>()
    val syncPhotosFromAPI: LiveData<Result<Unit>> = _syncResult

    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    var currentPage = 1
        private set
    private val pageSize = 20
    var isLoading = false
        private set
    var isLastPage = false
        private set

    private val loadedPhotos = mutableListOf<PhotoItem>()

    fun loadInitialPhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        listener?.isLoading()
        ECOLog.showLog("-1")
        currentPage = 1
        isLastPage = false
        loadedPhotos.clear()
        loadPhotos(onLoaded)
    }

    private fun loadPhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        ECOLog.showLog("-2")
        isLoading = true
        viewModelScope.launch {
            try {
                val response = photoService.getPhotos(currentPage, pageSize)
                if (response.isSuccessful) {
                    val newData = response.body().orEmpty()
                    loadedPhotos.addAll(newData)
                    _photos.postValue(loadedPhotos)
                    onLoaded(newData)
                    listener?.isLoaded()
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
            }
        }
    }

    fun loadMorePhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        ECOLog.showLog("-3")
        if (isLoading || isLastPage) return
        loadPhotos(onLoaded)
    }

    fun syncPhotos() {
        ECOLog.showLog("2")
        viewModelScope.launch {
            val result = photoRepository.syncPhotosFromAPI()
            _syncResult.postValue(result)
        }
    }

    fun getAllPhotos() = photoRepository.getAllPhotosPaging()

    fun searchPhoto(query: String) = photoRepository.searchPhoto(query)
}