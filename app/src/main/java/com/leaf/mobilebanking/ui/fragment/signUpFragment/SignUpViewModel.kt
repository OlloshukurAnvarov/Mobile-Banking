package com.leaf.mobilebanking.ui.fragment.signUpFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.domain.SignUpUseCache
import com.leaf.mobilebanking.repository.signUpRepository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCache: SignUpUseCache) :
    ViewModel() {

    private val _openVerifyFlow = MutableSharedFlow<Unit>()
    val openVerifyFlow: SharedFlow<Unit> = _openVerifyFlow

    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun signUp(firstName: String, lastName: String, password: String, phone: String) {
        viewModelScope.launch {
            val state = signUpUseCache(firstName, lastName, password, phone)
            handleState(state)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _openVerifyFlow.emit(Unit)
        }
    }

}