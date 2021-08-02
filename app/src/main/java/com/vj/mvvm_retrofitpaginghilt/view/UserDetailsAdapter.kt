package com.vj.mvvm_retrofitpaginghilt.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vj.mvvm_retrofitpaginghilt.databinding.LayoutDetailsItemBinding
import com.vj.mvvm_retrofitpaginghilt.model.data.UserLabelValue

class UserDetailsAdapter : RecyclerView.Adapter<UserDetailsAdapter.DataViewHolder>() {

    private val gitUsers: ArrayList<UserLabelValue> = arrayListOf()

    inner class DataViewHolder(itemDetailsView: LayoutDetailsItemBinding) :
        RecyclerView.ViewHolder(itemDetailsView.root) {
        var layoutDetailsItemBinding: LayoutDetailsItemBinding = itemDetailsView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = gitUsers.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.layoutDetailsItemBinding.detailedList = gitUsers[position]
        holder.layoutDetailsItemBinding.executePendingBindings()
    }

    fun addGitUsers(gitUsers: ArrayList<UserLabelValue>) {
        this.gitUsers.apply {
            clear()
            addAll(gitUsers)
        }
    }
}