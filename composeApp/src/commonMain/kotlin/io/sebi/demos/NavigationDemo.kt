package io.sebi.demos

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.toRoute
import com.matkovivan.nav_cupcake.ui.theme.MyAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview


val fruitEmojis = listOf("🍎", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🍒", "🥑")
//val fruitEmojis = listOf("Apple", "Orange", "Lemon", "Banana", "Melon", "Grape", "Strawberry", "Cherry", "Avocado")

@Preview
@Composable
fun StartPagePreview() {
    MyAppTheme {
        StartPage { }
    }
}

@Preview
@Composable
fun FruitFormPreview() {
    MyAppTheme {
        FruitForm("🍎", 0, {}, {}, {})
    }
}

@Serializable
data object Start

@Serializable
data class FruitForm(val fruit: String)

@Composable
fun NavigationDemoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Start,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeContent),
        enterTransition = enterT,
        exitTransition = exitT,
        popExitTransition = popExitT,
        popEnterTransition = popEnterT
    ) {

        composable<Start> {
            StartPage(onClickFruit = { fruit ->
                navController.navigate(FruitForm(fruit))
            })
        }
        composable<FruitForm> { backStackEntry ->
            val currentFruit = backStackEntry.toRoute<FruitForm>().fruit
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
    onNavigate: (FruitForm) -> Unit,
    onBack: () -> Unit,
    bottomSlot: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(60.dp),
        modifier = Modifier.background(Color.White)
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
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (fruit in fruitEmojis - currentFruit) {
                Text(fruit, fontSize = 40.sp, modifier = Modifier.clickable {
                    onNavigate(FruitForm(fruit))
                })
            }
        }
        bottomSlot()
    }
}

@Composable
private fun FruitPage(
    currentFruit: String,
    onNavigate: (FruitForm) -> Unit,
    onBack: () -> Unit,
    bottomSlot: @Composable ColumnScope.() -> Unit = {},
) {
    val viewModel = viewModel { FruitViewModel() }
    LaunchedEffect(viewModel) {
        viewModel.startGrowing(currentFruit)
    }
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text("All fruits", fontSize = 40.sp)
        for (fruit in fruitEmojis) {
            Text(fruit, fontSize = 60.sp, modifier = Modifier.clickable {
                onClickFruit(fruit)
            })
        }
    }
}

@Composable
fun Breadcrumbs(navController: NavHostController) {
    val state by navController.currentBackStack.collectAsState()
    val crumbs = state.mapNotNull { it.arguments?.getString("fruit") }.joinToString(" - ")
    Text(crumbs)
}