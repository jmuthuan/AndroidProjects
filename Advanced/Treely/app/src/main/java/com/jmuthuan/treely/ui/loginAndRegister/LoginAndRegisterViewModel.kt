package com.jmuthuan.treely.ui.loginAndRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmuthuan.treely.data.repository.AuthRepository
import com.jmuthuan.treely.shared.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginAndRegisterViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private var _registerState = MutableStateFlow(value = RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        authRepository.registerUser(email = email, password = password).collectLatest { result ->
            when(result) {
                is Resource.Loading -> {
                    _registerState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _registerState.update { it.copy(isSuccess = "Register Successful!") }
                }
                is Resource.Error -> {
                    _registerState.update { it.copy(isError = result.message) }
                }
            }
        }
    }

    //TODO add login function and check password lenght > 6

}

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String? = ""
)