package com.jiahaoliuliu.viewmodelflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jiahaoliuliu.viewmodelflow.ui.theme.ViewModelFlowTheme
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val flowViewModel: FlowViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            lifecycleScope.launch {
            val uiState = flowViewModel.states.collectAsStateWithLifecycle()
            when(uiState.value) {
                is UiState.Loaded -> SuccessUi(number = (uiState.value as UiState.Loaded).number)
                is UiState.Error -> {}
                UiState.Loading -> {}
            }
        }
    }
}

@Composable
fun SuccessUi(number: Int) {
    ViewModelFlowTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Text(
                    number.toString(),
                    modifier = Modifier.padding(innerPadding)
                )
            }
        )
    }
}