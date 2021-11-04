package com.example.mvvmcoroutinesroomhiltsample.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmcoroutinesroomhiltsample.data.repository.MainRepository
import com.example.mvvmcoroutinesroomhiltsample.ui.home.HomeViewModel

class ViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}