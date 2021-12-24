package com.example.mvvmcoroutinesroomhiltsample.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmcoroutinesroomhiltsample.data.local.entity.Note
import com.example.mvvmcoroutinesroomhiltsample.data.model.ApiUser
import com.example.mvvmcoroutinesroomhiltsample.data.repository.MainRepository
import com.example.mvvmcoroutinesroomhiltsample.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text*/
    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    private val notes = MutableLiveData<Resource<List<Note>>>()

    init {
        //fetchUsers()
        fetchNotes()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val usersFromApi = mainRepository.getUsers()
                users.postValue(Resource.success(usersFromApi))
            } catch (e: Exception) {
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }
    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }



    /*
    * NOTES FUNCTIONS
    */

    private fun fetchNotes() {
        viewModelScope.launch {
            notes.postValue(Resource.loading(null))
            try {
                val notesFromDb = mainRepository.getNotes()
                notes.postValue(Resource.success(notesFromDb))
            } catch (e: Exception) {
                notes.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getNotes(): LiveData<Resource<List<Note>>> {
        return notes
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            notes.postValue(Resource.loading(null))
            try {
                val insertResult = mainRepository.insert(note)
                Log.d("Room", insertResult.toString())
                val notesFromDb = mainRepository.getNotes()
                notes.postValue(Resource.success(notesFromDb))
            } catch (e: Exception) {
                notes.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            notes.postValue(Resource.loading(null))
            try {
                mainRepository.delete(note)
                val notesFromDb = mainRepository.getNotes()
                notes.postValue(Resource.success(notesFromDb))
            } catch (e: Exception) {
                notes.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}