package com.dicoding.picodiploma.mysubmission.api

import com.dicoding.picodiploma.mysubmission.model.Items
import com.dicoding.picodiploma.mysubmission.model.ResponseDetail
import com.dicoding.picodiploma.mysubmission.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<ResponseUser>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<ResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Items>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Items>>
}
