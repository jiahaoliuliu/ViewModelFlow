package com.jiahaoliuliu.viewmodelflow.ui.screens.multiplesendersqueue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiahaoliuliu.viewmodelflow.domain.GetRandomNumberUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class MultipleSendersQueueViewModel(
    private val getRandomNumberUseCase: GetRandomNumberUseCase = GetRandomNumberUseCase()
) : ViewModel() {
    private val requestSources:MutableSet<Source> = HashSet()
    private val fetchRequest = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val sourceStates = MutableStateFlow<Set<Source>>(requestSources)

    @OptIn(ExperimentalCoroutinesApi::class)
    val states: StateFlow<UiState> = fetchRequest.
            flatMapLatest {
                flow {
                    println("Source: $requestSources")
                    emit(UiState.Loading(requestSources))
                    getRandomNumberUseCase.invokeFlow(1000, delay = 3_000)
                        .take(1)
                        .collect { value ->
                            emit(UiState.Loaded(value, HashSet(requestSources)))
                            requestSources.clear()
                        }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = UiState.Initial
            )

    fun fetchData(source: Source) {
        println("Fetching data")
        viewModelScope.launch {
            requestSources.add(source)
            println("Request sources: $requestSources")
            sourceStates.emit(requestSources)
        }
        fetchRequest.tryEmit(Unit)
    }
}