package com.dicoding.picodiploma.mysubmission.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mysubmission.api.ApiConfig
import com.dicoding.picodiploma.mysubmission.model.ResponseDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val users = MutableLiveData<ResponseDetail>()
    val errors = MutableLiveData<String>()

    fun setUserDetail(username: String) {
        ApiConfig.getApiService().getDetail(username)
            .enqueue(object : Callback<ResponseDetail> {
                override fun onResponse(
                    call: Call<ResponseDetail>,
                    response: Response<ResponseDetail>
                ) {
                    if (response.isSuccessful) users.postValue(response.body())
                    else errors.postValue("Request failed: ${response.code()}")
                }

                override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                    errors.postValue("Request failed: ${t.message}")
                }
            })
    }

    fun getUserDetail() = users

    fun getError() = errors
}