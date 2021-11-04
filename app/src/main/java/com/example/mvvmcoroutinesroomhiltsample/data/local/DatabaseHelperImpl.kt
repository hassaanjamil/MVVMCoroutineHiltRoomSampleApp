package com.example.mvvmcoroutinesroomhiltsample.data.local

import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.User
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

}