package com.example.booklibrary.ui.screens.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.repository.photo.PhotoRepository
import com.example.booklibrary.data.repository.photo.paging.PhotoPagingSource
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val photoRepository: PhotoRepository,
    private val photoService: PhotoService,
) : ViewModel() {

    private val _syncResult = MutableLiveData<Result<Unit>>()
    val syncPhotosFromAPI: LiveData<Result<Unit>> = _syncResult
    var listener: PhotoListener? = null

    val flow: Flow<PagingData<PhotoItem>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotoPagingSource(photoService) }
    ).flow.cachedIn(viewModelScope)

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