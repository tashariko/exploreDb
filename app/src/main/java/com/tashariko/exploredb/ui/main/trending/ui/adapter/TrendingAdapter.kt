package com.tashariko.exploredb.ui.main.trending.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.databinding.ListItemLoadingErrorBinding
import com.tashariko.exploredb.databinding.ListItemTrendingBinding
import com.tashariko.exploredb.ui.main.detail.ItemDetailActivity

class TrendingAdapter : PagingDataAdapter<TrendingItem, TrendingViewHolder>(
    CustomDiffCallback()
){

    private lateinit var imagePaths: Triple<String, String, String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            ListItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            imagePaths
        )
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setBaseImageUrl(imagePath: Triple<String, String, String>) {
        this.imagePaths = imagePath
    }
}


class TrendingViewHolder(
    private val binding: ListItemTrendingBinding,
    val imagePaths: Triple<String, String, String>
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.parentView.setOnClickListener { itemView.context.startActivity(Intent(itemView.context, ItemDetailActivity::class.java)) }
    }

    fun bind(trendingItem: TrendingItem) {
        trendingItem.originalTitle?.let {
            binding.titleView.text = it
        }

        trendingItem.overview?.let {
            binding.overviewView.text = it
        }

        //base url: https://image.tmdb.org/t/p/
        // size : w500/
        // path: 8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
        trendingItem.posterPath?.let {
            binding.imageView.load("${imagePaths.first}${imagePaths.third}${it}")
        }

    }

}



private class CustomDiffCallback : DiffUtil.ItemCallback<TrendingItem>() {

    override fun areItemsTheSame(oldItem: TrendingItem, newItem: TrendingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrendingItem, newItem: TrendingItem): Boolean {
        return oldItem == newItem
    }
}