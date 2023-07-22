package com.ajm.kotlin.mvvm.Ui.User.ViewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajm.kotlin.mvvm.Data.Remote.ApiService.ApiService
import com.ajm.kotlin.mvvm.Data.Remote.Client.RetrofitClient
import com.ajm.kotlin.mvvm.Data.Remote.Response.UserApiResponse.DataItem
import com.ajm.kotlin.mvvm.Data.Remote.Response.UserApiResponse.UserAPiResposne
import com.ajm.kotlin.mvvm.Data.Repository.AppRepositoryImpl
import com.ajm.kotlin.mvvm.Utils.Resource
import com.ajm.kotlin.mvvm.Utils.mutableEventFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response


class UserViewModel() : ViewModel() {
    private val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val repository = AppRepositoryImpl(apiService)

    val userLIst = MutableLiveData<List<DataItem?>?>()


    fun delateItem(item: DataItem) {
        val currentList = userLIst.value.orEmpty().toMutableList()
        currentList.remove(item)
        userLIst.value = currentList

    }
    fun updateNames(id :Int,firstname: String? ,lastName: String? ) {
        getItem(id)?.firstName = firstname
        getItem(id)?.lastName = lastName
    }

    fun getItem(item: Int): DataItem? {
        return userLIst.value?.find { it?.id == item }
    }

    private val _getUserListApiState =
        mutableEventFlow<Resource<List<DataItem?>?>>()
    val getUserListApiState
        get() = _getUserListApiState.asSharedFlow()

    fun getUserList() {
        viewModelScope.launch {
            val response = repository.getUserList();

            _getUserListApiState.tryEmit(Resource.Loading())

            response.enqueue(object : retrofit2.Callback<UserAPiResposne?> {
                override fun onResponse(
                    call: retrofit2.Call<UserAPiResposne?>,
                    response: Response<UserAPiResposne?>
                ) {
                    userLIst.value = response?.body()?.data;
                    _getUserListApiState.tryEmit(Resource.Success(response?.body()?.data))

                }

                override fun onFailure(
                    call: retrofit2.Call<UserAPiResposne?>,
                    t: Throwable
                ) {
                    _getUserListApiState.tryEmit(Resource.Error(t.message))

                }

            })

        }


    }

}