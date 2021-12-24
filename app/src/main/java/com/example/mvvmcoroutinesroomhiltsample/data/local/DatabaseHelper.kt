package com.example.mvvmcoroutinesroomhiltsample.data.local

import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

    suspend fun getNotes(): List<Note>

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

}