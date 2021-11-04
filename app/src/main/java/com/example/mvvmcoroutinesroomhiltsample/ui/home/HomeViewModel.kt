package com.example.mvvmcoroutinesroomhiltsample.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser
import com.example.mvvmcoroutinesroomhiltsample.data.repository.MainRepository
import com.example.mvvmcoroutinesroomhiltsample.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text*/
    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val usersFromApi = mainRepository.getUsers()
                users.postValue(Resource.success(usersFromApi))
            } catch (e: Exception) {
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }
}