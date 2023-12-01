package com.leaf.mobilebanking.ui.fragment.signInFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.domain.SignInUseCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCache: SignInUseCache,
    private val settings: Settings
) : ViewModel() {

    private val _openSecurityFlow = MutableSharedFlow<Unit>()
    val openSecurityFlow: SharedFlow<Unit> = _openSecurityFlow

    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow: SharedFlow<Int> = _errorFlow

    private val _errorIOFlow = MutableSharedFlow<String>()
    val errorIOFlow: SharedFlow<String> = _errorIOFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun agreedCookies() {
        settings.cookies = true
    }

    fun isAgreed() = settings.cookies

    fun signIn(password: String, phone: String) {
        viewModelScope.launch {
            val state = signInUseCache(password, phone)
            handleState(state)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Error -> _errorFlow.emit(state.code)
            is State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _openSecurityFlow.emit(Unit)
            is State.ErrorIO -> _errorIOFlow.emit(state.message)
        }
    }

}