package com.leaf.mobilebanking.ui.fragment.refactorCardFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.domain.AddCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefactorCardViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val settings: Settings
) : ViewModel() {

    private val _successFlow = MutableSharedFlow<Unit>()
    val successFlow: SharedFlow<Unit> = _successFlow

    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow: SharedFlow<Int> = _errorFlow

    private val _errorIOFlow = MutableSharedFlow<String>()
    val errorIOFlow: SharedFlow<String> = _errorIOFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun addCard(
        expireMonth: Int,
        expireYear: Int,
        name: String,
        pan: String
    ) {
        viewModelScope.launch {
            val state = addCardUseCase(settings.accessToken, expireMonth, expireYear, name, pan)
            handleState(state)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Error -> _errorFlow.emit(state.code)
            is State.ErrorIO -> _errorIOFlow.emit(state.message)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _successFlow.emit(Unit)
        }
    }

}