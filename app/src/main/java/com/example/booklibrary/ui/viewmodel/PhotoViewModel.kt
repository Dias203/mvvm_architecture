package com.example.booklibrary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.repository.PhotoRepository
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.launch

class PhotoViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    private val _syncResult = MutableLiveData<Result<Unit>>()
    fun syncPhotosFromAPI(): LiveData<Result<Unit>> = _syncResult

    init {
        syncPhotos()
    }

    private fun syncPhotos() {
        ECOLog.showLog("123123")
        viewModelScope.launch {
            _syncResult.postValue(photoRepository.syncPhotosFromAPI())
        }
    }

    fun getAllPhotos() = photoRepository.getAllPhoto()

    fun searchPhoto(query: String) = photoRepository.searchPhoto(query)
}