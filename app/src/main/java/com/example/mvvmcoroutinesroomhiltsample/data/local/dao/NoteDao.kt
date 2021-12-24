package com.example.mvvmcoroutinesroomhiltsample.data.local.dao

import androidx.room.*
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    suspend fun getNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}