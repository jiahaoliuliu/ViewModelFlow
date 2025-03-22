package com.jiahaoliuliu.viewmodelflow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.stateIn

class FlowViewModel(
//    private val savedStateHandle: SavedStateHandle,
    private val getRandomNumberUseCase: GetRandomNumberUseCase = GetRandomNumberUseCase()
) : ViewModel() {

    val states: StateFlow<UiState> =
//        savedStateHandle
//        .getStateFlow(MY_ID, initialValue = 0)
//        .flatMapLatest { id ->
//            flow {
//                emit(getRandomNumberUseCase.invoke())
//            }
        flow {
            while (true) {
                delay(1_000)
                emit(UiState.Loaded(getRandomNumberUseCase.invoke()))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = UiState.Loading
        )

    companion object {
        private const val MY_ID = "1234567890"
    }
}