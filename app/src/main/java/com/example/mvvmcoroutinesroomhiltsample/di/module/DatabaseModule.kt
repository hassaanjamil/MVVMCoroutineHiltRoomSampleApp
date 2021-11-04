package com.example.mvvmcoroutinesroomhiltsample.di.module

import android.content.Context
import androidx.room.Room
import com.example.mvvmcoroutinesroomhiltsample.data.local.AppDatabase
import com.example.mvvmcoroutinesroomhiltsample.data.local.DatabaseBuilder
import com.example.mvvmcoroutinesroomhiltsample.data.local.DatabaseHelper
import com.example.mvvmcoroutinesroomhiltsample.data.local.DatabaseHelperImpl
import com.example.mvvmcoroutinesroomhiltsample.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "mindorks-example-coroutines"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChannelDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(@ApplicationContext context: Context, userDao: UserDao) : DatabaseHelper =
        DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
}