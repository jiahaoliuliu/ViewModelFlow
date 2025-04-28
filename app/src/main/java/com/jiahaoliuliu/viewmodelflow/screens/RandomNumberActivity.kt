package com.jiahaoliuliu.viewmodelflow.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jiahaoliuliu.viewmodelflow.FlowViewModel
import com.jiahaoliuliu.viewmodelflow.UiState
import com.jiahaoliuliu.viewmodelflow.ui.theme.ViewModelFlowTheme

class RandomNumberActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: FlowViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val uiState = viewModel.states.collectAsStateWithLifecycle()
            ViewModelFlowTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Content(uiState.value, viewModel)
                        }
                    }
                )
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RandomNumberActivity::class.java)
        }
    }
}

@Composable
private fun Content(uiState: UiState, viewModel: FlowViewModel) {
    when (uiState) {
        is UiState.Loaded -> {
            println("The number is ${uiState.number}")
            SuccessUi(
                number = uiState.number,
                viewModel = viewModel
            )
        }

        is UiState.Error -> {}
        UiState.Loading -> Loading()
    }
}

@Composable
fun Loading() {
    ViewModelFlowTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        )
    }
}

@Composable
fun SuccessUi(
    number: Int,
    viewModel: FlowViewModel,
) {
    ViewModelFlowTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = number.toString(),
                        fontSize = 30.sp
                    )

                    Button(onClick = viewModel::fetchData) {
                        Text("Fetch new number")
                    }
                }
            }
        )
    }
}