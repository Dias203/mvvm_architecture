package com.example.booklibrary.ui.screens.photo

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun PhotoActivity.setupRecyclerView() {
    binding.listPhoto.apply {
        layoutManager = GridLayoutManager(this@setupRecyclerView, 3, GridLayoutManager.VERTICAL, false)
        adapter = photoAdapter

    }

    lifecycleScope.launch {
        photoViewModel.flow.collectLatest { pagingData ->
            photoAdapter.submitData(pagingData)
        }
    }

    /*photoViewModel.getAllPhotos().observe(this, Observer { photos ->
        photoAdapter.differ.submitList(photos.reversed())
        updateUI(photos)
    })


    photoViewModel.syncPhotosFromAPI().observe(this, Observer { result ->
       ECOLog.showLog("111222333")
        result.onFailure {
            ECOLog.showLog("445566")
            if (photoAdapter.differ.currentList.isEmpty()) {
                Toast.makeText(this, "Failed to load photos and no cached data available!", Toast.LENGTH_LONG).show()
                binding.listPhoto.visibility = View.GONE
            } else {
                Toast.makeText(this, "Failed to load photos, showing cached data.", Toast.LENGTH_SHORT).show()
            }
        }
    })*/
}

fun PhotoActivity.registerListener() {
    ECOLog.showLog("1")
    photoViewModel.listener = object : PhotoListener {
        override fun isLoading() {
            ECOLog.showLog("9812312")
            binding.progressLoading.visibility = View.VISIBLE
        }

        override fun isLoaded() {
            ECOLog.showLog("9812312")
            binding.progressLoading.visibility = View.GONE
        }

        override fun isLoadFail() {
            TODO("Not yet implemented")
        }

    }
}

fun PhotoActivity.loadStateListener() {
    photoAdapter.addLoadStateListener { loadStates ->
        val isLoading = loadStates.source.refresh is LoadState.Loading
        ECOLog.showLog("Loading state: $isLoading")
        binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

fun PhotoActivity.updateUI(photos: List<PhotoItem>?) {
    if (photos != null) {
        if (photos.isNotEmpty()) {
            binding.listPhoto.visibility = View.VISIBLE
        } else {
            binding.listPhoto.visibility = View.GONE
        }
    }
}