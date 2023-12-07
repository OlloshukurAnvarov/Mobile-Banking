package com.leaf.mobilebanking.ui.fragment.confirmationFragment

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.MyService
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.domain.ConfirmUseCase
import com.leaf.mobilebanking.domain.VerifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val verifyUseCase: ConfirmUseCase,
    private val settings: Settings
) :
    ViewModel() {

    private val _successMessageFlow = MutableSharedFlow<Unit>()
    val successMessageFlow: SharedFlow<Unit> = _successMessageFlow

    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow: SharedFlow<Int> = _errorFlow

    private val _errorIOFlow = MutableSharedFlow<String>()
    val errorIOFlow: SharedFlow<String> = _errorIOFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun verify(code: String) {
        viewModelScope.launch {
            val state = verifyUseCase(settings.temporaryToken, code)
            handleState(state)
        }
    }

    fun sendSMS(context: Context) {
        MyService.code = settings.code
        context.startService(Intent(context, MyService::class.java))
    }

    fun stopService(context: Context) {
        context.stopService(Intent(context, MyService::class.java))
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Error -> _errorFlow.emit(state.code)
            is State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _successMessageFlow.emit(Unit)
            is State.ErrorIO -> _errorIOFlow.emit(state.message)
        }
    }

}