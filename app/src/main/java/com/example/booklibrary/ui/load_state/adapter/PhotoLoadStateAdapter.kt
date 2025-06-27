package com.example.booklibrary.ui.load_state.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibrary.databinding.LoadStateItemBinding

class PhotoLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PhotoLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        private val binding: LoadStateItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root){

        private val retry: Button = binding.retryButton
            .also {
                it.setOnClickListener { retry() }
            }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retry.isVisible = loadState is LoadState.Error
                errorMsg.isVisible = loadState is LoadState.Error

                if(loadState is LoadState.Error) {
                    errorMsg.text = loadState.error.localizedMessage
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val binding = LoadStateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding, retry)
    }
}