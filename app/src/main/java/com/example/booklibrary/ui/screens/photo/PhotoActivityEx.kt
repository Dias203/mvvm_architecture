package com.example.booklibrary.ui.screens.photo
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibrary.data.model.DataState
import com.example.booklibrary.data.model.photo.PhotoItem

fun PhotoActivity.setupRecyclerView() {
    binding.listPhoto.apply {
        layoutManager =
            GridLayoutManager(this@setupRecyclerView, 3, GridLayoutManager.VERTICAL, false).apply {
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
                    loadMoreData()
                }
            }
        })
    }
}

fun PhotoActivity.loadMoreData() {
    photoAdapter.addLoadingFooter()
    photoViewModel.loadMorePhotos { newItems ->
        photoAdapter.addData(newItems)
    }
}

fun PhotoActivity.loadInitialData() {
    photoViewModel.loadInitialPhotos { initialList ->
        photoAdapter.setData(initialList)
    }
}

fun PhotoActivity.registerDataState() {
    photoViewModel.dataState.observe(this) { state ->
        when (state) {
            is DataState.Loading -> { showLoadingUI() }

            is DataState.Loaded -> { showData(state.data) }

            is DataState.Error -> { showError(state.exception.message) }
        }
    }
}

fun PhotoActivity.showLoadingUI() {
    binding.progressLoading.visibility = View.VISIBLE
}

fun PhotoActivity.showData(data: List<PhotoItem>) {
    binding.progressLoading.visibility = View.GONE
    photoAdapter.setData(data)

}

fun PhotoActivity.showError(message: String?) {
    Toast.makeText(this@showError, message, Toast.LENGTH_SHORT).show()
}
