package com.dicoding.picodiploma.mysubmission.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("items")
    val items: ArrayList<Items>

)