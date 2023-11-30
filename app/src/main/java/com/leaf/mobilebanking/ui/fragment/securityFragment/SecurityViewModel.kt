package com.leaf.mobilebanking.ui.fragment.securityFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.model.Password
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.repository.securityRepository.SecurityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val repository: SecurityRepository,
    private val settings: Settings
) : ViewModel() {

    private val _password = MutableSharedFlow<String>()
    val password: SharedFlow<String> = _password

    fun savePassword(password: String) {
        viewModelScope.launch {
            repository.savePassword(password)
        }
    }

    fun makeReadyPass() {
        viewModelScope.launch {
            val pass = repository.password()
            if (isLogged())
                _password.emit(pass.password)
        }
    }

    fun isLogged() = settings.accessToken != null

    fun safe() {
        settings.apply {
            accessToken = temporaryToken
            temporaryToken = null
        }
    }

}