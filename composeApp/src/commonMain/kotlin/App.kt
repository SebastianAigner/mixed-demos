import androidx.compose.runtime.Composable
import androidx.navigation.Navigation
import com.matkovivan.nav_cupcake.CupcakeApp
import com.matkovivan.nav_cupcake.ui.theme.CupcakeTheme
import io.sebi.demos.*

@Composable
fun App() {
    CupcakeTheme {
        NavigationDemoApp()
    }
}


//@Composable
//fun App() {
//    CupcakeTheme {
//        CupcakeApp()
//    }
//}
