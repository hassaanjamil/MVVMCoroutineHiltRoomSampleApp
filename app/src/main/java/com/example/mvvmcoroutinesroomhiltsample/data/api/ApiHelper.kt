package com.example.mvvmcoroutinesroomhiltsample.data.api

import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser

interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>

}