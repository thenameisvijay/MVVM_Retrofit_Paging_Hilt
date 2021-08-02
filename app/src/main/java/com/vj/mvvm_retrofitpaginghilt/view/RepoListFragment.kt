package com.vj.mvvm_retrofitpaginghilt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vj.mvvm_retrofitpaginghilt.databinding.FragmentRepoListBinding
import com.vj.mvvm_retrofitpaginghilt.helper.NetworkHelper
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
                header = RepoLoadStateAdapter { mAdapter.retry() },
                footer = RepoLoadStateAdapter { mAdapter.retry() }
            )
        }
    }

    private fun setupObservers() {
        if (NetworkHelper.hasInternet(requireContext())) {
            repoViewModel.repoList.observe(viewLifecycleOwner) {
                mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

            mAdapter.addLoadStateListener { loadState ->
                binding.apply {
                    mProgress.isVisible = loadState.source.refresh is LoadState.Loading
                    mRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                    if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        mAdapter.itemCount < 1
                    ) {
                        mRecyclerView.isVisible = false
                    }
                }
            }
        } else {
            mProgress.visibility = View.GONE
            mRecyclerView.visibility = View.GONE
            binding.errorMesg.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRepoListBinding = null
    }

    override fun onRepoItemClicked(name: String, avatar: String) {
        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(name, avatar)
        findNavController().navigate(action)
    }
}