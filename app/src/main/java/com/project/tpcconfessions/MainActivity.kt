package com.project.tpcconfessions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.tpcconfessions.ui.theme.TPCConfessionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TPCConfessionsTheme {

                val viewModel: ConfessionViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    ) { innerPadding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = innerPadding)) {
                        SheetComposable(viewModel = viewModel)
                        Body(innerPadding = innerPadding, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Body(innerPadding: PaddingValues, viewModel: ConfessionViewModel){
    val context = LocalContext.current
    var rollNo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = rollNo,
            onValueChange = { rollNo = it },
            label = { Text("Roll No") }
        )
        Button(onClick = { viewModel.fetchConfessions(rollNo, context) }) {
            Text("Fetch Confessions")
        }
        val confessions by viewModel.confessions.collectAsState()
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text("Showing Confessions here",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp))
                if(confessions.isEmpty())
                    Text("No Confessions to show")
            }
            items(confessions.size){confessionIndex ->
                ConfessionCard(confessions[confessionIndex])
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SheetComposable(viewModel: ConfessionViewModel) {
    var rollNo by remember { mutableStateOf("") }
    var confession by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = rollNo,
            onValueChange = { rollNo = it },
            label = { Text("Roll No") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confession,
            onValueChange = { confession = it },
            label = { Text("Confession") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.postConfession(BodyDataEntity(rollNo, confession), context) }) {
            Text("Submit Confession")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ConfessionCard(confession: DataEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Roll No: ${confession.roll_no}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Confession: ${confession.confession}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarTPC() {
    LargeTopAppBar(
        title = {
            Text(text = "TPC Confessions",
                style = MaterialTheme.typography.headlineMedium)
        },
        actions = {
            // Add action buttons here
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), // Increase the height as per your requirement
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),

    )
}