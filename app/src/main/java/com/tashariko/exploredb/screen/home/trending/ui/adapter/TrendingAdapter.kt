package com.tashariko.exploredb.screen.home.trending.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.databinding.ListItemTrendingBinding
import com.tashariko.exploredb.screen.movieDetail.MovieDetailActivity

class TrendingAdapter : PagingDataAdapter<TrendingItem, TrendingViewHolder>(
    CustomDiffCallback()
) {

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
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var trendingItem: TrendingItem

    init {
        binding.parentView.setOnClickListener {
            if (trendingItem.mediaType == AppConstants.MediaType.MOVIE.value) {
                MovieDetailActivity.launchActivity(itemView.context, trendingItem.id)
            } else {
                Toast.makeText(itemView.context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun bind(trendingItem: TrendingItem) {
        this.trendingItem = trendingItem
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
            binding.imageView.load("${imagePaths.first}${imagePaths.third}${it}") {
                placeholder(R.drawable.icon_movie)
            }
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