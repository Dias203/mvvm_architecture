package com.example.booklibrary.ui.screens.photo


import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun PhotoActivity.setupRecyclerView() {
    binding.listPhoto.apply {
        layoutManager = GridLayoutManager(this@setupRecyclerView, 3, GridLayoutManager.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (photoAdapter.getItemViewType(position) == photoAdapter.VIEW_TYPE_LOADING) 3 else 1
                }
            }

        }
        adapter = photoAdapter

        // Thêm OnScrollListener để phát hiện LoadMore
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Kiểm tra nếu cuộn đến gần cuối danh sách (cách 5 item) và không đang tải
                if (!photoViewModel.isLoading && !photoViewModel.isLastPage && lastVisibleItem + 5 >= totalItemCount) {
                    photoAdapter.addLoadingFooter()
                    photoViewModel.loadMorePhotos { newItems ->
                        photoAdapter.removeLoadingFooter()
                        photoAdapter.addData(newItems)
                    }
                }
            }
        })
    }

    // Load dữ liệu ban đầu
    photoViewModel.loadInitialPhotos { initialList ->
        photoAdapter.setData(initialList)
    }
}

fun PhotoActivity.registerListener() {
    photoViewModel.listener = object : PhotoListener {
        override fun isLoading() {
            binding.progressLoading.visibility = View.VISIBLE
        }

        override fun isLoaded() {
            binding.progressLoading.visibility = View.GONE
        }

        override fun isLoadFail() {
            Toast.makeText(this@registerListener, "Load Fail", Toast.LENGTH_SHORT).show()
        }

    }
}