package com.example.a20220726_eriknurja_nycschools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20220726_eriknurja_nycschools.api.SchoolRepository
import com.example.a20220726_eriknurja_nycschools.model.NYCSchool
import com.example.a20220726_eriknurja_nycschools.view.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val repository: SchoolRepository
    ): ViewModel() {

    private val _schoolLiveData: MutableLiveData<UIState> = MutableLiveData()
    val schoolLiveData: LiveData<UIState> get() = _schoolLiveData

    private val _scoreLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.Loading)
    val scoreLiveData: LiveData<UIState> get() = _scoreLiveData

    var currentSchool: NYCSchool? = null

    init {
        fetchNYCSchools()
    }

    private fun fetchNYCSchools() {
        viewModelScope.launch {
            repository.fetchNYCSchools().collect { state ->
                _schoolLiveData.postValue(state)
            }
        }
    }

    fun fetchNYCScore(dbn: String) {
        viewModelScope.launch {
            repository.fetchNYCScore(dbn).collect { state ->
                _scoreLiveData.postValue(state)
            }
        }
    }

    fun setSchool(nycSchool: NYCSchool?) {
        currentSchool = nycSchool
        _scoreLiveData.value = UIState.Loading
    }
}