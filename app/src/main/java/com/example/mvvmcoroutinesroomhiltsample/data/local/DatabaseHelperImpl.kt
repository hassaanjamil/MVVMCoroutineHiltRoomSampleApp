package com.example.mvvmcoroutinesroomhiltsample.data.local

import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.User
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()
    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

    override suspend fun getNotes(): List<Note> = appDatabase.noteDao().getNotes()
    override suspend fun insert(note: Note) = appDatabase.noteDao().insert(note)
    override suspend fun delete(note: Note) = appDatabase.noteDao().delete(note)
}