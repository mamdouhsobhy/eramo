package com.example.eramo.home.presentation.fragment.postDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.eramo.core.presentation.base.BaseFragmentBinding
import com.example.eramo.core.presentation.base.BaseState
import com.example.eramo.core.presentation.extensions.showGenericAlertDialog
import com.example.eramo.core.presentation.extensions.showToast
import com.example.eramo.databinding.FragmentPostDetailsBinding
import com.example.eramo.home.data.responseremote.getPostDetails.ModelGetPostDetailsResponseRemote
import com.example.eramo.home.presentation.fragment.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentPostDetails : BaseFragmentBinding<FragmentPostDetailsBinding>() {

    private val args: FragmentPostDetailsArgs by navArgs()

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerOnView()
        observeStateFlow()
        viewModel.getPostsDetails(args.postId)

    }

    private fun addListenerOnView() {

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getPostsDetails(args.postId)
        }

    }


    private fun observeStateFlow() {
        viewModel.postDetailsState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handlePostDetailsStateChange(state) }
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
            binding.layoutPostDetails.isVisible = false
            binding.shimmerContainer.isVisible = true
            binding.shimmerContainer.startShimmerAnimation()
        } else {
            binding.layoutPostDetails.isVisible = true
            binding.shimmerContainer.isVisible = false
            binding.shimmerContainer.stopShimmerAnimation()
        }
    }

    private fun handlePostDetailsStateChange(state: BaseState<ModelGetPostDetailsResponseRemote>) {
        when (state) {
            is BaseState.Init -> Unit
            is BaseState.Error -> handleError(
                state.code,
                state.message
            )

            is BaseState.Success -> handleSuccessGetPostDetails(state.items)
            is BaseState.ShowToast -> requireActivity().showToast(
                state.message,
                state.isConnectionError
            )

            is BaseState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleSuccessGetPostDetails(items: ModelGetPostDetailsResponseRemote?) {
        binding.tvTitleValue.text = items?.title
        binding.tvBodyValue.text = items?.body
    }

}