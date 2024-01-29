package com.example.eramo.home.domain.interactor

import com.example.eramo.core.presentation.base.BaseState
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemote
import com.example.eramo.home.data.responseremote.getPostDetails.ModelGetPostDetailsResponseRemote
import com.example.eramo.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun executeGetPosts(): Flow<BaseState<ModelGetPostsResponseRemote>> {
        return homeRepository.getPosts()
    }

    suspend fun executeGetPostsDetails(postId:String): Flow<BaseState<ModelGetPostDetailsResponseRemote>> {
        return homeRepository.getPostsDetails(postId)
    }

}