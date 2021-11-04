package com.example.mvvmcoroutinesroomhiltsample.data.repository

import com.example.mvvmcoroutinesroomhiltsample.data.api.ApiHelper
import com.example.mvvmcoroutinesroomhiltsample.data.local.DatabaseHelper
import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) {

    suspend fun getUsers(): List<ApiUser> {
        return apiHelper.getUsers()
    }

}