package com.example.mvvmcoroutinesroomhiltsample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmcoroutinesroomhiltsample.data.local.dao.UserDao
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}