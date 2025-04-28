package com.jiahaoliuliu.viewmodelflow.ui.screens.multiplesendersqueue

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jiahaoliuliu.viewmodelflow.UiState
import com.jiahaoliuliu.viewmodelflow.ui.theme.ViewModelFlowTheme

class MultipleSendersQueueActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MultipleSendersQueueViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val uiState = viewModel.states.collectAsStateWithLifecycle()
            ViewModelFlowTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Multiple senders queue",
                                    fontSize = 20.sp
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                        )

                    },
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Companion.Center
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
            return Intent(context, MultipleSendersQueueActivity::class.java)
        }
    }
}

@Composable
private fun Content(uiState: UiState, viewModel: MultipleSendersQueueViewModel) {
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
private fun Loading() {
    ViewModelFlowTheme {
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            content = { innerPadding ->
                Box(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        )
    }
}

@Composable
private fun SuccessUi(
    number: Int,
    viewModel: MultipleSendersQueueViewModel,
) {
    ViewModelFlowTheme {
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            content = { innerPadding ->
                Column(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Companion.CenterHorizontally
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