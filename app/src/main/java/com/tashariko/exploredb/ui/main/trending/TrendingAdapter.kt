package com.tashariko.exploredb.ui.main.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tashariko.exploredb.R
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.databinding.ListItemTrendingBinding
import java.util.ArrayList

class TrendingAdapter : RecyclerView.Adapter<TrendingViewHolder>(){

    private var dataList = ArrayList<TrendingItem>()

    fun submitList(arrayList: ArrayList<TrendingItem>) {
        dataList.clear()
        dataList.addAll(arrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(ListItemTrendingBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(dataList[position])
    }
}

class TrendingViewHolder(private val binding: ListItemTrendingBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(trendingItem: TrendingItem) {
        binding.titleView.text = trendingItem.title
    }

}