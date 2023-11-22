package com.leaf.mobilebanking.ui.fragment.signInFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.mobilebanking.data.model.SignInBody
import com.leaf.mobilebanking.repository.signInRepository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
}