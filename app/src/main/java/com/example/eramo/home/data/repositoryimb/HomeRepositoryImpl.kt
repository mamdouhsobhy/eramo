package com.example.eramo.home.data.repositoryimb

import com.example.eramo.core.data.utils.EmittingResponse
import com.example.eramo.core.presentation.base.BaseState
import com.example.eramo.home.data.datasource.HomeService
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemote
import com.example.eramo.home.data.responseremote.getPostDetails.ModelGetPostDetailsResponseRemote
import com.example.eramo.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val emittingResponse: EmittingResponse
) : HomeRepository {

    override suspend fun getPosts(): Flow<BaseState<ModelGetPostsResponseRemote>> {
        return emittingResponse.makeApiCall { homeService.getPosts() }
    }

    override suspend fun getPostsDetails(postId:String): Flow<BaseState<ModelGetPostDetailsResponseRemote>> {
        return emittingResponse.makeApiCall { homeService.getPostsDetails(postId) }
    }
}