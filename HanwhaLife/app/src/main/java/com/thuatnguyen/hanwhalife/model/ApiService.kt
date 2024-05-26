package com.thuatnguyen.hanwhalife.model

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api-tinhthanh/1/0.htm")
    fun getProvinces(): Call<ProvinceResponse>
}