package com.jiahaoliuliu.viewmodelflow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take

class FlowViewModel(
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val getRandomNumberUseCase: GetRandomNumberUseCase = GetRandomNumberUseCase()
) : ViewModel() {
    val fetchRequest = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val states: StateFlow<UiState> =
        savedStateHandle.getStateFlow(MY_ID, initialValue = 1000)
            .flatMapLatest { storedValue ->
                fetchRequest.onStart {
                    fetchData()
                }.flatMapLatest { backendData ->
                    flow {
                        getRandomNumberUseCase.invokeFlow(storedValue)
                            .take(2)
                            .collect { value ->
                                emit(UiState.Loaded(value))
                            }
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = UiState.Loading
            )

    fun fetchData() {
        println("Fetching data")
        fetchRequest.tryEmit(Unit)
    }

    companion object {
        private const val MY_ID = "1234567890"
    }
}