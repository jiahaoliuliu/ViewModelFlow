package com.jiahaoliuliu.viewmodelflow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class FlowViewModel(
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val getRandomNumberUseCase: GetRandomNumberUseCase = GetRandomNumberUseCase()
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val states: StateFlow<UiState> =
        savedStateHandle.getStateFlow(MY_ID, initialValue = 1000)
            .flatMapLatest { until ->
                flow {
                    getRandomNumberUseCase.invokeFlow(until)
                        .collect { value ->
                            emit(UiState.Loaded(value))
                        }
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