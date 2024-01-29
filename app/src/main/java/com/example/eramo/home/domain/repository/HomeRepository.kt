package com.example.eramo.home.domain.repository

import com.example.eramo.core.presentation.base.BaseState
import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemote
import com.example.eramo.home.data.responseremote.getPostDetails.ModelGetPostDetailsResponseRemote
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getPosts(): Flow<BaseState<ModelGetPostsResponseRemote>>

    suspend fun getPostsDetails(postId:String): Flow<BaseState<ModelGetPostDetailsResponseRemote>>

}