package com.example.booklibrary.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.databinding.PhotoLayoutBinding
import com.example.booklibrary.ui.view.screens.PhotoDetailActivity

class PhotoAdapter(private val context: Context) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    class PhotoViewHolder(val itemBinding: PhotoLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.albumId == newItem.albumId
                    && oldItem.author == newItem.author
                    && oldItem.url == newItem.url
                    && oldItem.downloadUrl == newItem.downloadUrl
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPhoto = differ.currentList[position]

        holder.itemBinding.tvPhotoTitle.text = currentPhoto.author
        Glide.with(context)
            .load(currentPhoto.downloadUrl)
            .into(holder.itemBinding.imgView)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, PhotoDetailActivity::class.java).apply {
                putExtra("photo", currentPhoto)
            }
            it.context.startActivity(intent)
        }
    }


}