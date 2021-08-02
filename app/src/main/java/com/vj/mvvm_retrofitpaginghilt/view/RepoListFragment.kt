package com.vj.mvvm_retrofitpaginghilt.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vj.mvvm_retrofitpaginghilt.databinding.FragmentRepoListBinding
import com.vj.mvvm_retrofitpaginghilt.helper.NetworkHelper
import com.vj.mvvm_retrofitpaginghilt.model.data.Item
import com.vj.mvvm_retrofitpaginghilt.network.Status
import com.vj.mvvm_retrofitpaginghilt.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment(), RepoPagingAdapter.RepoItemClickListener {

    private val repoViewModel: RepoViewModel by activityViewModels()
    private lateinit var mProgress: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RepoPagingAdapter

    private var fragmentRepoListBinding: FragmentRepoListBinding? = null
    private val binding get() = fragmentRepoListBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentRepoListBinding = FragmentRepoListBinding.inflate(inflater, container, false)
        setupUI()
        setupObservers()
        return binding.root
    }

    private fun setupUI() {
        mRecyclerView = binding.repoList
        mProgress = binding.progress
        mAdapter = RepoPagingAdapter(this)
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = RepoLoadStateAdapter{mAdapter.retry()},
                footer = RepoLoadStateAdapter{mAdapter.retry()}
            )
        }
    }

    private fun setupObservers() {
        if (NetworkHelper.hasInternet(requireContext())) {
            repoViewModel.getRepoList().observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mRecyclerView.visibility = View.VISIBLE
                            mProgress.visibility = View.GONE
                            resource.data?.let { gitUsers ->
                                setToAdapter(gitUsers)
                            }
                        }
                        Status.ERROR -> {
                            mRecyclerView.visibility = View.VISIBLE
                            mProgress.visibility = View.GONE
                            Log.e("ERROR", "error msg: " + it.message)
                        }
                        Status.LOADING -> {
                            mProgress.visibility = View.VISIBLE
                            mRecyclerView.visibility = View.GONE
                        }
                    }
                }
            })
        } else {
            mProgress.visibility = View.GONE
            mRecyclerView.visibility = View.GONE
            binding.errorMesg.visibility = View.VISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setToAdapter(gitUsers: LiveData<PagingData<Item>>) {
        mAdapter.apply {
            addGitUsers(gitUsers)
            notifyDataSetChanged()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        fragmentRepoListBinding = null
    }

    override fun onRepoItemClicked(name: String, avatar: String) {

    }
}