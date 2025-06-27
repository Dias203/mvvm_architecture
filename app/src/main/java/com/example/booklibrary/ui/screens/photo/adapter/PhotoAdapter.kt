package com.example.booklibrary.ui.screens.photo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.databinding.PhotoLayoutBinding
import com.example.booklibrary.ui.screens.photo_detail.PhotoDetailActivity

class PhotoAdapter(private val context: Context) : PagingDataAdapter<PhotoItem, PhotoAdapter.PhotoViewHolder>(
    PhotoDiffCallback()
) {
    class PhotoViewHolder(val itemBinding: PhotoLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private class PhotoDiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPhoto = getItem(position)

        currentPhoto?.let { photoItem ->
            holder.itemBinding.tvPhotoTitle.text = photoItem.author
            Glide.with(context)
                .load(photoItem.downloadUrl)
                .into(holder.itemBinding.imgView)

            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, PhotoDetailActivity::class.java).apply {
                    putExtra("photo", currentPhoto)
                }
                it.context.startActivity(intent)
            }
        }
    }

}