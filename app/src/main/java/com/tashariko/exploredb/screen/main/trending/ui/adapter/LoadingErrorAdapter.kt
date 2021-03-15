package com.tashariko.exploredb.screen.main.trending.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tashariko.exploredb.R
import com.tashariko.exploredb.databinding.ListItemLoadingErrorBinding
import com.tashariko.exploredb.databinding.ListItemTrendingBinding

class LoadingErrorAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingErrorAdapter.LoaderViewHolder>() {
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder = LoaderViewHolder.getInstance(parent,retry)


    class LoaderViewHolder(val binding: ListItemLoadingErrorBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder = LoaderViewHolder(
                ListItemLoadingErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                retry
            )
        }

        init {
            binding.retryView.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.progressView.isVisible = true
                binding.mssageView.text = binding.root.context.getString(R.string.error_layout_loading_text)
                binding.retryView.isVisible = false
            } else {
                binding.progressView.isVisible = false
                binding.mssageView.text = binding.root.context.getString(R.string.generic_error_message)
                binding.retryView.isVisible = true
            }
        }
    }

}