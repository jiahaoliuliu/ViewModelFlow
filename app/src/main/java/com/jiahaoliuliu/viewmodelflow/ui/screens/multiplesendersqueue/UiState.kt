package com.jiahaoliuliu.viewmodelflow.ui.screens.multiplesendersqueue

sealed class UiState {
    data object Initial: UiState()
    data class Loading(val sources: Set<Source> = emptySet()): UiState()
    data class Loaded(val number: Int, val sources: Set<Source>): UiState()
    data class Error(val errorMessage: String): UiState()
}