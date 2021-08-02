package com.vj.mvvm_retrofitpaginghilt.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vj.mvvm_retrofitpaginghilt.R
import com.vj.mvvm_retrofitpaginghilt.databinding.FragmentRepoDetailsBinding
import com.vj.mvvm_retrofitpaginghilt.helper.loadImage
import com.vj.mvvm_retrofitpaginghilt.model.data.UserLabelValue
import com.vj.mvvm_retrofitpaginghilt.network.Status
import com.vj.mvvm_retrofitpaginghilt.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private val repoViewModel: RepoViewModel by activityViewModels()
    private lateinit var mProgress: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: UserDetailsAdapter
    private lateinit var profilePic: AppCompatImageView
    private var fragmentRepoDetailsBinding: FragmentRepoDetailsBinding? = null
    private val binding get() = fragmentRepoDetailsBinding!!
    private val userDetails: RepoDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentRepoDetailsBinding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        setupUI()
        gotoDetailsDetails(userDetails.userArgValue)
        return binding.root
    }

    private fun setupUI() {
        profilePic = binding.avatarPic
        loadImage(profilePic, userDetails.userAvatarArgValue)
        mRecyclerView = binding.userDetailsList
        mProgress = binding.progress
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = UserDetailsAdapter()
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                mRecyclerView.context,
                (mRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        mRecyclerView.adapter = mAdapter
    }

    private fun gotoDetailsDetails(loginName: String) {
        repoViewModel.getUserDetails(loginName).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mRecyclerView.visibility = View.VISIBLE
                        mProgress.visibility = View.GONE
                        resource.data?.let { gitUsers ->
                            val userDetailsList: ArrayList<UserLabelValue> = arrayListOf()
                            userDetailsList.add(UserLabelValue("Name: ", gitUsers.name))
                            userDetailsList.add(UserLabelValue("Followers: ", gitUsers.followers.toString()))
                            userDetailsList.add(UserLabelValue("Following: ", gitUsers.following.toString()))
                            userDetailsList.add(UserLabelValue("Company: ", gitUsers.company))
                            userDetailsList.add(UserLabelValue("Location: ", gitUsers.location))
                            setToAdapter(userDetailsList)
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
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setToAdapter(gitUsers: ArrayList<UserLabelValue>) {
        mAdapter.apply {
            addGitUsers(gitUsers)
            notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRepoDetailsBinding = null
    }
}