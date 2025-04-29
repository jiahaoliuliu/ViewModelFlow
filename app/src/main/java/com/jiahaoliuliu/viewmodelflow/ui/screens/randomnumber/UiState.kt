package com.jiahaoliuliu.viewmodelflow.ui.screens.randomnumber

sealed class UiState {
    data object Loading: UiState()
    data class Loaded(val number: Int): UiState()
    data class Error(val errorMessage: String): UiState()
}