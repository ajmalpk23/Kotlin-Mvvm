package com.ajm.kotlin.mvvm.Data.Remote.ApiService

import com.ajm.kotlin.mvvm.Data.Remote.Response.UserApiResponse.UserAPiResposne

import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("users?per_page=100")
    fun init():Call<UserAPiResposne>
}