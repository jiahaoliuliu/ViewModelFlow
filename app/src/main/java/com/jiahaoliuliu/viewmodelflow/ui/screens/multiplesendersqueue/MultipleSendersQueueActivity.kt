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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jiahaoliuliu.viewmodelflow.ui.theme.ViewModelFlowTheme

class MultipleSendersQueueActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MultipleSendersQueueViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val uiState = viewModel.states.collectAsStateWithLifecycle()
            val sourcesSet = viewModel.sourceStates.collectAsStateWithLifecycle()
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
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            RequestButtons(viewModel)
//                            SourcesSet(sourcesSet.value)
                            RandomNumberContent(uiState.value)
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
private fun RequestButtons(viewModel: MultipleSendersQueueViewModel) {
    Row(Modifier.padding(16.dp)) {
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            onClick = { viewModel.fetchData(Source.QUEUE_1) }) {
            Text("Fetch 1")
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            onClick = { viewModel.fetchData(Source.QUEUE_2) }) {
            Text("Fetch 2")
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            onClick = { viewModel.fetchData(Source.QUEUE_3) }) {
            Text("Fetch  3")
        }
    }
}

@Composable
private fun SourcesSet(sourcesSet: Set<Source>) {
    println("Sources: $sourcesSet")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Sources: ${sourcesSet.joinToString(",")}",
            fontSize = 20.sp
        )
    }
}

@Composable
private fun RandomNumberContent(uiState: UiState) {
    when (uiState) {
        UiState.Initial -> {}
        is UiState.Loading -> Loading()
        is UiState.Loaded -> {
            println("The number is ${uiState.number} and the sources are ${uiState.sources}")
            SuccessUi(
                number = uiState.number,
                sourcesSet = uiState.sources
            )
        }

        is UiState.Error -> {}
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.Companion
            .fillMaxSize(),
        contentAlignment = Alignment.Companion.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessUi(
    number: Int,
    sourcesSet: Set<Source>
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                fontSize = 30.sp
            )
        }
        Text(
            text = "Sources: ${sourcesSet.joinToString(",")}",
            fontSize = 20.sp
        )
    }
}