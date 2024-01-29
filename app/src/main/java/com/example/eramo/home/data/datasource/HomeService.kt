package com.example.eramo.home.data.datasource

import com.example.eramo.home.data.responseremote.getPost.ModelGetPostsResponseRemote
import com.example.eramo.home.data.responseremote.getPostDetails.ModelGetPostDetailsResponseRemote
import retrofit2.Response
import retrofit2.http.*

interface HomeService {

    @GET("posts")
    suspend fun getPosts(): Response<ModelGetPostsResponseRemote>

    @GET("posts/{id}")
    suspend fun getPostsDetails(@Path("id") id:String): Response<ModelGetPostDetailsResponseRemote>
}