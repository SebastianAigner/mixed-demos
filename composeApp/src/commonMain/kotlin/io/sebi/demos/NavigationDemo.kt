package io.sebi.demos

import MyViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


val fruitEmojis = listOf("🍎", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🍒")

@Preview
@Composable
fun StartPagePreview() {
    StartPage { }
}

@Preview
@Composable
fun FruitForm() {
    FruitForm("🍎", 0, {}, {}, {})
}

@Composable
fun NavigationDemoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "start",
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.safeContent)
    ) {
        composable("start") {
            StartPage(onClickFruit = { fruit ->
                navController.navigate("fruitForm/$fruit")
            })
        }
        composable("fruitForm/{fruit}") { backStackEntry ->
            val currentFruit = backStackEntry.arguments?.getString("fruit") ?: "No fruit"
            FruitPage(
                currentFruit = currentFruit,
                onNavigate = { navController.navigate(it) },
                onBack = { navController.popBackStack() },
                bottomSlot = { Breadcrumbs(navController) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FruitForm(
    currentFruit: String,
    growCount: Int,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    bottomSlot: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        Button(onClick = { onBack() }, modifier = Modifier.align(Alignment.Start)) {
            Text("Back")
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentFruit,
                fontSize = 80.sp
            )
            Text("$growCount grown on this page")
        }

        var note by rememberSaveable { mutableStateOf("") }
        TextField(note, onValueChange = { note = it })
        Text("Related fruits")
        FlowRow {
            for (fruit in fruitEmojis - currentFruit) {
                Text(fruit, fontSize = 40.sp, modifier = Modifier.clickable {
                    onNavigate("fruitForm/$fruit")
                })
            }
        }
        bottomSlot()
    }
}

@Composable
private fun FruitPage(
    currentFruit: String,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    bottomSlot: @Composable ColumnScope.() -> Unit = {},
) {
    val viewModel = viewModel { FruitViewModel().apply { startGrowing(currentFruit) } }
    val growCount by viewModel.grownFruitCount.collectAsStateWithLifecycle()
    FruitForm(
        currentFruit = currentFruit,
        growCount = growCount,
        onNavigate = onNavigate,
        onBack = onBack,
        bottomSlot = bottomSlot
    )
}


@Composable
private fun StartPage(onClickFruit: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text("All fruits", fontSize = 20.sp)
        for (fruit in fruitEmojis) {
            Text(fruit, fontSize = 40.sp, modifier = Modifier.clickable {
                onClickFruit(fruit)
            })
        }
    }
}

@Composable
fun Breadcrumbs(navController: NavHostController) {
    val state by navController.currentBackStack.collectAsState()
    val crumbs = state.mapNotNull { it.arguments?.getString("fruit") }.joinToString(" → ")
    Text(crumbs)
}