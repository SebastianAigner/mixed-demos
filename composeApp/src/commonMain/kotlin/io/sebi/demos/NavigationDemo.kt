package io.sebi.demos

import MyViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NavigationDemoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "start",
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        composable("start") {
            Button(onClick = {
                navController.navigate("lifecycle")
            }) {
                Text("Lifecycle time!")
            }
        }
        composable("lifecycle") {
            Column {
                LifecycleApp()
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Back")
                }
                Button(onClick = {
                    navController.navigate("lifecycle")
                }) {
                    Text("Forward")
                }
            }
        }
    }
}

@Composable
fun NavigationDemoScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "start",
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        composable("start") { entry ->
            Start(onGo = {
                navController.navigate("form")
            })
        }
        composable("form") { backStackEntry ->
            Form(onBack = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun Start(onGo: () -> Unit) {
    val vm = viewModel { MyViewModel() }
    val counter by remember { mutableStateOf(0) }
    val savedEntriesCount by vm.savedEntryNumber.collectAsState()
    Column {
        Text("Welcome to the io.sebi.demos.Start ($counter)")
        Button({ vm.saveEntry("Hello!") }) {
            Text("Save")
        }
        Text("Saved so far: $savedEntriesCount")
        Button(onGo) {
            Text("Go")
        }
    }

}

@Composable
fun Form(onBack: () -> Unit) {
    Column {
        Text("Welcome to the io.sebi.demos.Form")
        Button(onBack) {
            Text("Back")
        }
    }
}