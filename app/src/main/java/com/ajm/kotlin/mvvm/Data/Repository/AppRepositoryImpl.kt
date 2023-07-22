package com.ajm.kotlin.mvvm.Data.Repository


import com.ajm.kotlin.mvvm.Data.Remote.ApiService.ApiService

class AppRepositoryImpl constructor(private val retrofitService: ApiService) {


    fun getUserList()= retrofitService.init()


}