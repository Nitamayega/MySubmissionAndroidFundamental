package com.dicoding.picodiploma.mysubmission.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mysubmission.api.ApiConfig
import com.dicoding.picodiploma.mysubmission.model.Items
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowsViewModel : ViewModel() {

    private val apiService = ApiConfig.getApiService()

    fun getFollows(username: String, isFollowers: Boolean): LiveData<ArrayList<Items>> {
        val followsLiveData = MutableLiveData<ArrayList<Items>>()
        val call =
            if (isFollowers) apiService.getFollowers(username) else apiService.getFollowing(username)
        call.enqueue(object : Callback<ArrayList<Items>> {
            override fun onResponse(
                call: Call<ArrayList<Items>>,
                response: Response<ArrayList<Items>>
            ) {
                if (response.isSuccessful) {
                    followsLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<Items>>, t: Throwable) {
                Log.d("Failure", t.message ?: "")
            }
        })
        return followsLiveData
    }
}