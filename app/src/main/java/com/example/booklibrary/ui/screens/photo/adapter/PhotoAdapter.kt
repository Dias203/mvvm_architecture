package com.example.booklibrary.ui.screens.photo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.databinding.LoadStateItemBinding
import com.example.booklibrary.databinding.PhotoLayoutBinding
import com.example.booklibrary.ui.screens.photoDetail.PhotoDetailActivity

class PhotoAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    val VIEW_TYPE_LOADING = 1

    private val photos = mutableListOf<PhotoItem?>()
    private var isLoadingAdded = false

    fun setData(newData: List<PhotoItem>) {
        photos.clear()
        photos.addAll(newData)
        notifyDataSetChanged()
    }


    fun addData(newItems: List<PhotoItem>) {
        val start = photos.size
        removeLoadingFooter()
        photos.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size)
    }

    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            photos.add(null)
            notifyItemInserted(photos.size - 1)
        }
    }

    private fun removeLoadingFooter() {
        if (isLoadingAdded && photos.isNotEmpty() && photos.last() == null) {
            val position = photos.size - 1
            photos.removeAt(position)
            notifyItemRemoved(position)
            isLoadingAdded = false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (photos[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = PhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PhotoViewHolder(binding)
        } else {
            val binding = LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                photos[position]?.let { holder.bind(it) }
            }
            is LoadingViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int = photos.size

    inner class PhotoViewHolder(private val binding: PhotoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoItem: PhotoItem) {
            binding.tvPhotoTitle.text = photoItem.author
            Glide.with(context)
                .load(photoItem.downloadUrl)
                .into(binding.imgView)

            binding.root.setOnClickListener {
                val intent = Intent(context, PhotoDetailActivity::class.java)
                intent.putExtra("photo", photoItem)
                context.startActivity(intent)
            }
        }
    }

    inner class LoadingViewHolder(private val binding: LoadStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.progressBar.visibility = View.VISIBLE
            binding.retryButton.visibility = View.GONE
            binding.errorMsg.visibility = View.GONE
        }
    }
}