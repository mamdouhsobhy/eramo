package com.example.eramo.home.presentation.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eramo.core.presentation.base.BaseFragmentBinding
import com.example.eramo.core.presentation.base.BaseState
import com.example.eramo.core.presentation.extensions.TxT
import com.example.eramo.core.presentation.extensions.onTextChange
import com.example.eramo.core.presentation.extensions.safeNavigate
import com.example.eramo.core.presentation.extensions.showGenericAlertDialog
import com.example.eramo.core.presentation.extensions.showToast
import com.example.eramo.databinding.FragmentHomeBinding
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemote
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemoteItem
import dagger.hilt.android.AndroidEntryPoint
import com.example.eramo.home.presentation.fragment.home.adapter.AdapterPosts
import com.example.eramo.home.presentation.fragment.home.viewModel.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentHome : BaseFragmentBinding<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    private val adapterPosts by lazy {
        AdapterPosts(itemSelected = {
            findNavController().safeNavigate(
                FragmentHomeDirections.actionFragmentHomeToFragmentPostDetails(
                    it
                )
            )
        })
    }

    private var posts = ArrayList<ModelGetPostsResponseRemoteItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerOnView()
        observeStateFlow()

        viewModel.getPosts()
    }

    private fun addListenerOnView() {

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getPosts()
        }

        binding.edSearch.onTextChange {
            if(it.isNotEmpty()) {
                val newList = java.util.ArrayList<ModelGetPostsResponseRemoteItem>()
                for (item in posts) {
                    if (item.title?.contains(it) == true) {
                        newList.add(item)
                    }
                }
                adapterPosts.submitList(newList.sortedBy { it -> it.title })
                binding.rvPosts.scrollToPosition(0)
            }else{
                adapterPosts.submitList(posts)
                binding.rvPosts.scrollToPosition(0)
            }

            binding.imgClear.isVisible = it.isNotEmpty()
        }

        binding.imgClear.setOnClickListener {
            binding.edSearch.setText("")
        }
    }

    private fun setUpPrograms(posts: ArrayList<ModelGetPostsResponseRemoteItem>) {
        binding.rvPosts.adapter = adapterPosts
        adapterPosts.submitList(posts)
    }

    private fun observeStateFlow() {
        viewModel.postState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleGetPostsStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleError(errorCode: String, errorMessage: String) {
        requireActivity().showGenericAlertDialog(
            parentFragmentManager,
            errorCode.toInt(),
            errorMessage
        )
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.swipeToRefresh.isRefreshing = false
        if (isLoading) {
            binding.layoutHome.isVisible = false
            binding.shimmerContainer.isVisible = true
            binding.shimmerContainer.startShimmerAnimation()
        } else {
            binding.layoutHome.isVisible = true
            binding.shimmerContainer.isVisible = false
            binding.shimmerContainer.stopShimmerAnimation()
        }
    }

    private fun handleGetPostsStateChange(state: BaseState<ModelGetPostsResponseRemote>) {
        when (state) {
            is BaseState.Init -> Unit
            is BaseState.Error -> handleError(
                state.code,
                state.message
            )

            is BaseState.Success -> handleSuccessGetPosts(state.items)
            is BaseState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )

            is BaseState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleSuccessGetPosts(items: ModelGetPostsResponseRemote?) {
        posts.clear()
        if (items != null) {
            posts = items
            setUpPrograms(items)
            if (binding.edSearch.TxT().isNotEmpty()) {
                binding.edSearch.setText(binding.edSearch.TxT())
            }
        }
    }

}