package com.example.booklibrary.ui.screens.photo

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booklibrary.ui.load_state.adapter.PhotoLoadStateAdapter
import com.example.booklibrary.utils.ECOLog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun PhotoActivity.setupRecyclerView() {
    binding.listPhoto.apply {
        layoutManager = GridLayoutManager(this@setupRecyclerView, 3, GridLayoutManager.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // Nếu item tại position là footer (LoadStateAdapter), chiếm toàn bộ số cột
                    return if (adapter?.getItemViewType(position) == photoAdapter.getItemViewType(position)) 1 else 3
                }
            }
        }
        adapter = photoAdapter.withLoadStateFooter(
            footer = PhotoLoadStateAdapter { photoAdapter.retry() }
        )
    }

    lifecycleScope.launch {
        photoViewModel.flow.collectLatest { pagingData ->
            ECOLog.showLog("9998887")
            photoAdapter.submitData(pagingData)
        }
    }

    lifecycleScope.launch {
        photoAdapter.loadStateFlow.collectLatest { loadStates ->
            val isLoading = loadStates.refresh is LoadState.Loading
            binding.progressLoading.isVisible = isLoading

            val isError = loadStates.refresh is LoadState.Error
            if (isError) {
                val error = (loadStates.refresh as LoadState.Error).error
                Toast.makeText(this@setupRecyclerView, "Lỗi: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
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