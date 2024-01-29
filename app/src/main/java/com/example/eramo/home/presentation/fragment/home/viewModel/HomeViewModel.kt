package com.example.eramo.home.presentation.fragment.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eramo.core.data.utils.ExecuteResponse
import com.example.eramo.core.presentation.base.BaseState
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemote
import com.example.eramo.home.data.responseremote.getPostDetails.ModelGetPostDetailsResponseRemote
import com.example.eramo.home.domain.interactor.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val executeResponse: ExecuteResponse,
) : ViewModel() {

    private val _postState = MutableStateFlow<BaseState<ModelGetPostsResponseRemote>>(BaseState.Init)
    val postState: StateFlow<BaseState<ModelGetPostsResponseRemote>> get() = _postState

    private val _postDetailsState = MutableStateFlow<BaseState<ModelGetPostDetailsResponseRemote>>(BaseState.Init)
    val postDetailsState: StateFlow<BaseState<ModelGetPostDetailsResponseRemote>> get() = _postDetailsState

    fun getPosts() {
        viewModelScope.launch {
            executeResponse.execute(homeUseCase.executeGetPosts(), _postState)
                .collect {
                    _postState.value = it
                }
        }
    }

    fun getPostsDetails(postId:String) {
        viewModelScope.launch {
            executeResponse.execute(homeUseCase.executeGetPostsDetails(postId), _postDetailsState)
                .collect {
                    _postDetailsState.value = it
                }
        }
    }

}
