import androidx.compose.runtime.Composable
import androidx.navigation.Navigation
import com.matkovivan.nav_cupcake.CupcakeApp
import com.matkovivan.nav_cupcake.ui.theme.MyAppTheme
import io.sebi.demos.*

@Composable
fun App() {
    MyAppTheme {
        NavigationDemoApp()
    }
}


//@Composable
//fun App() {
//    CupcakeTheme {
//        CupcakeApp()
//    }
//}
