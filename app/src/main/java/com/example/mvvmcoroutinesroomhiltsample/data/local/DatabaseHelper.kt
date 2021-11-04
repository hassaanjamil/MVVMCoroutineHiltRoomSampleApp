package com.example.mvvmcoroutinesroomhiltsample.data.local

import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

}