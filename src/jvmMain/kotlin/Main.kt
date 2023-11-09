import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skia.Image
import java.io.File

@Composable
@Preview
fun App() {
    val file = remember { File("resources\\UI-QuestGreeting-TopLeft.png") }
    val font = remember {
        FontFamily(
            Font(
                resource = "font\\MORPHEUS.TTF",
                style = FontStyle.Normal
            )
        )
    }

    MaterialTheme {
        Image(
            bitmap = Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap(),
            ""
        )
        Text(
            modifier = Modifier.graphicsLayer {
                translationY = 82f
                translationX = 31f
            },
            style = TextStyle(
                fontFamily = font,
                fontSize = 18.sp,
            ),
            text = "Wool Would Work"
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
