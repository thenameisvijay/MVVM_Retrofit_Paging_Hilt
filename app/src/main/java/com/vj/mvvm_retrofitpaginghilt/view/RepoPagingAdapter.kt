package com.vj.mvvm_retrofitpaginghilt.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vj.mvvm_retrofitpaginghilt.databinding.LayoutItemBinding
import com.vj.mvvm_retrofitpaginghilt.model.data.Item


class RepoPagingAdapter(private val repoItemClickListener: RepoItemClickListener) :
    PagingDataAdapter<Item, RepoPagingAdapter.DataViewHolder>(RepoComparator) {

    inner class DataViewHolder(itemView: LayoutItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var layoutItemBinding: LayoutItemBinding = itemView
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.layoutItemBinding.repoList = getItem(position)
        holder.layoutItemBinding.repoItemClickListener = repoItemClickListener
        holder.layoutItemBinding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    interface RepoItemClickListener {
        fun onRepoItemClicked(name: String, avatar: String)
    }

    companion object {
        private val RepoComparator = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem

        }
    }

}