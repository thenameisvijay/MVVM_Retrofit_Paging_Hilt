package com.vj.mvvm_retrofitpaginghilt.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vj.mvvm_retrofitpaginghilt.databinding.LayoutItemBinding
import com.vj.mvvm_retrofitpaginghilt.databinding.LoadStateFooterBinding

class RepoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RepoLoadStateAdapter.LoadStateViewHolder>() {


    inner class LoadStateViewHolder(private val binding: LoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var loadStateFooterBinding: LoadStateFooterBinding = binding

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            LoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}