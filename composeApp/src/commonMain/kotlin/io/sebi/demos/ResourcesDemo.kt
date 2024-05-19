import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matkovivan.nav_cupcake.ui.theme.MyAppTheme
import cupcake.composeapp.generated.resources.Res
import cupcake.composeapp.generated.resources.conference_description
import cupcake.composeapp.generated.resources.menu_banner
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun ResourcesDemoApp() {
    MyAppTheme {
        Column(
            Modifier.fillMaxWidth().padding(70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(Res.drawable.menu_banner),
                "KotlinConf Banner"
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(
                    Res.string.conference_description,
                    "Multiplatform"
                ),
                fontSize = 30.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}