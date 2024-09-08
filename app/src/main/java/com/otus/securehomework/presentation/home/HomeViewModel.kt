package com.otus.securehomework.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.otus.securehomework.data.Response
import com.otus.securehomework.data.dto.LoginResponse
import com.otus.securehomework.data.repository.UserRepository
import com.otus.securehomework.data.source.local.UserPreferences
import com.otus.securehomework.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    val user: LiveData<Response<LoginResponse>>
        get() = _user

    val isBiometricEnabled: LiveData<Boolean?> = userPreferences.isBiometricEnabled.asLiveData()

    fun getUser() = viewModelScope.launch {
        _user.value = Response.Loading
        _user.value = repository.getUser()
    }

    fun onBiometricLoginEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.saveBiometricEnabled(enabled)
        }
    }
}