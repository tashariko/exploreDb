package com.tashariko.exploredb.ui.main.trending

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.tashariko.exploredb.R
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.databinding.ListItemTrendingBinding
import com.tashariko.exploredb.ui.main.detail.ItemDetailActivity
import java.util.ArrayList

class TrendingAdapter : RecyclerView.Adapter<TrendingViewHolder>(){

    private lateinit var imagePaths: Triple<String, String, String>
    private var dataList = ArrayList<TrendingItem>()

    fun submitList(arrayList: ArrayList<TrendingItem>) {
        dataList.clear()
        dataList.addAll(arrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(ListItemTrendingBinding.inflate(LayoutInflater.from(parent.context),parent, false), imagePaths)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(dataList[position])
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
        binding.titleView.text = trendingItem.originalTitle
        binding.overviewView.text = trendingItem.overview

        //base url: https://image.tmdb.org/t/p/
        // size : w500/
        // path: 8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
        if(trendingItem.posterPath.isNotEmpty()) {
            binding.imageView.load("${imagePaths.first}${imagePaths.third}${trendingItem.posterPath}")
        }
    }

}