package com.example.mvvmcoroutinesroomhiltsample.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long,
        @ColumnInfo(name = "desc") var desc: String,
        @ColumnInfo(name = "icon") var icon: Int
) {
        constructor(desc: String, icon: Int) : this(0, desc, icon)
}
