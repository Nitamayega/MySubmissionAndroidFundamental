package com.dicoding.picodiploma.mysubmission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mysubmission.api.ApiConfig
import com.dicoding.picodiploma.mysubmission.model.Items
import com.dicoding.picodiploma.mysubmission.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<ArrayList<Items>>()
    private val listUsers: LiveData<ArrayList<Items>> = _listUsers

    fun getSearchUsers(): LiveData<ArrayList<Items>> = this.listUsers
    fun setSearchUser(query: String) {
        ApiConfig.getApiService().getUser(query)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>, response: Response<ResponseUser>
                ) {
                    if (response.isSuccessful) {
                        _listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })

    }
}