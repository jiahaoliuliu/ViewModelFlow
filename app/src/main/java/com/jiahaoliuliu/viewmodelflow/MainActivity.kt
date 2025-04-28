package com.jiahaoliuliu.viewmodelflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jiahaoliuliu.viewmodelflow.ui.screens.multiplesendersqueue.MultipleSendersQueueActivity
import com.jiahaoliuliu.viewmodelflow.ui.screens.randomnumber.RandomNumberActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        modifier = Modifier
                            .clickable {
                                context.startActivity(
                                    RandomNumberActivity.createIntent(context)
                                )
                            }
                            .padding(16.dp),
                        text = "Random numbers")
                }
                item {
                    Text(
                        modifier = Modifier
                            .clickable {
                                context.startActivity(
                                    MultipleSendersQueueActivity.createIntent(context)
                                )
                            }
                            .padding(16.dp),
                        text = "Multiple senders queue")
                }
            }
        }
    }
}
