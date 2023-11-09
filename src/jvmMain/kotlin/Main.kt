import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skia.Image
import java.io.File

@Composable
@Preview
fun App() {
    val file = remember { File("resources\\UI-QuestGreeting-TopLeft.png") }

    MaterialTheme {
        Image(
            bitmap = Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap(),
            ""
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
