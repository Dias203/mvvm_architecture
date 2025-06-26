package com.example.booklibrary.ui.view.extensions

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.ui.view.screens.PhotoActivity
import com.example.booklibrary.utils.ECOLog

fun PhotoActivity.setupUI() {
    binding.listPhoto.apply {
        layoutManager = GridLayoutManager(this@setupUI, 3, GridLayoutManager.VERTICAL, false)
        adapter = photoAdapter
    }

    photoViewModel.getAllPhotos().observe(this, Observer { photos ->
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
    })
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