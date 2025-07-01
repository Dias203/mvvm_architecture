package com.example.booklibrary.ui.screens.photo

import android.provider.ContactsContract.Contacts.Photo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.model.DataState
import com.example.booklibrary.data.repository.photo.PhotoRepository
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel(
    private val photoRepository: PhotoRepository,
    private val photoService: PhotoService,
) : ViewModel() {
    private val _dataSate = MutableLiveData<DataState<List<PhotoItem>>>()
    val dataState: LiveData<DataState<List<PhotoItem>>> = _dataSate


    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    private var currentPage = 1
    private val pageSize = 20
    var isLoading = false
    var isLastPage = false
    var loaded = false

    private val loadedPhotos = mutableListOf<PhotoItem>()

    fun loadInitialPhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        _dataSate.postValue(DataState.Loading(isLoading = true))
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
                    _dataSate.postValue(DataState.Loaded(data = loadedPhotos))
                }
            } catch (e: Exception) {
                _dataSate.postValue(DataState.Error(e))
                ECOLog.showLog("Exception: $e")
            } finally {
                isLoading = false
                //_dataSate.postValue(DataState.Loading(isLoading = false))
            }
        }
    }

    fun loadMorePhotos(onLoaded: (List<PhotoItem>) -> Unit) {
        if (isLoading || isLastPage) return
        loadPhotos(onLoaded)
    }
}