package com.example.booklibrary.ui.screens.photo_detail

import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.booklibrary.R
import com.example.booklibrary.data.model.photo.PhotoItem

fun PhotoDetailActivity.getIntentExtra() {
    val photo = intent.getSerializableExtra("photo") as? PhotoItem
    if (photo != null) {
        currentPhoto = photo
        binding.idImg.text = getString(R.string.id_img_tv, photo.id.toString())
        //binding.albumIdImg.text = getString(R.string.albumId_img_tv, photo.albumId.toString())
        binding.titleImg.text = getString(R.string.title_img_tv, photo.author)
        binding.thumbnailUrlImg.text = getString(R.string.thumbnailUrl_img_tv, photo.downloadUrl)

        Glide.with(this)
            .load(photo.downloadUrl)
            .into(binding.detailImg)

    } else {
        Toast.makeText(this, "Error: Can't find note", Toast.LENGTH_SHORT).show()
        finish()
    }
}
