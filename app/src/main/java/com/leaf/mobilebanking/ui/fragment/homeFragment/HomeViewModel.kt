package com.leaf.mobilebanking.ui.fragment.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.domain.GetCardsUseCase
import com.leaf.mobilebanking.domain.entity.CardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val settings: Settings
) : ViewModel() {

    private val _cardsFlow = MutableSharedFlow<List<CardData>>()
    val cardsFlow: SharedFlow<List<CardData>> = _cardsFlow

    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow: SharedFlow<Int> = _errorFlow

    private val _errorIOFlow = MutableSharedFlow<String>()
    val errorIOFlow: SharedFlow<String> = _errorIOFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun cards() {
        viewModelScope.launch {
            val state = getCardsUseCase(settings.accessToken)
            handleState(state)
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
            is State.ErrorIO -> _errorIOFlow.emit(state.message)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
            is State.Success<*> -> _cardsFlow.emit(state.data as List<CardData>)
        }
    }

}