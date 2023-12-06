package com.leaf.mobilebanking.ui.fragment.transferFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.domain.GetCardsUseCase
import com.leaf.mobilebanking.domain.TransferUseCase
import com.leaf.mobilebanking.domain.entity.CardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val transferUseCase: TransferUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val settings: Settings
) :
    ViewModel() {

    private val _openVerifyFlow = MutableSharedFlow<Unit>()
    val openVerifyFlow: SharedFlow<Unit> = _openVerifyFlow

    private val _cardsFlow = MutableSharedFlow<List<CardData>>()
    val cardsFlow: SharedFlow<List<CardData>> = _cardsFlow

    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow: SharedFlow<Int> = _errorFlow

    private val _errorIOFlow = MutableSharedFlow<String>()
    val errorIOFlow: SharedFlow<String> = _errorIOFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun transferTo(from: Int?, to: String, amount: String?) {
        viewModelScope.launch {
            val state = transferUseCase(from, to, amount)
            handleState(state)
        }
    }

    fun cards() {
        viewModelScope.launch {
            val state = getCardsUseCase(settings.accessToken)
            handleStateCards(state)
        }
    }

    fun delaying() {
        viewModelScope.launch {
            delay(250)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Error -> _errorFlow.emit(state.code)
            is State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _openVerifyFlow.emit(Unit)
            is State.ErrorIO -> _errorIOFlow.emit(state.message)
        }
    }

    private suspend fun handleStateCards(state: State) {
        when (state) {
            is State.Error -> _errorFlow.emit(state.code)
            is State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _cardsFlow.emit(state.data as List<CardData>)
            is State.ErrorIO -> _errorIOFlow.emit(state.message)
        }
    }

}