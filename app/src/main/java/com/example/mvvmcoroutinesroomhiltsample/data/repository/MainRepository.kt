package com.example.mvvmcoroutinesroomhiltsample.data.repository

import android.util.Log
import com.example.mvvmcoroutinesroomhiltsample.data.api.ApiHelper
import com.example.mvvmcoroutinesroomhiltsample.data.local.DatabaseHelper
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note
import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) {

    suspend fun getUsers(): List<ApiUser> {
        return apiHelper.getUsers()
    }

    suspend fun getNotes(): List<Note> {
        return dbHelper.getNotes()
    }

    suspend fun insert(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.insert(note)
            Log.d("MainRepository", "Insert called")
        }
    }

    suspend fun delete(note: Note) {
        return dbHelper.delete(note)
    }
}