import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skia.Image
import java.io.File

@Composable
@Preview
fun App() {
    val topLeftImage = remember { File("resources\\UI-QuestGreeting-TopLeft.png") }
    val topRightImage = remember { File("resources\\UI-QuestGreeting-TopRight.png") }
    val bottomLeftImage = remember { File("resources\\UI-QuestGreeting-BotLeft.png") }
    val bottomRightImage = remember { File("resources\\UI-QuestGreeting-BotRight.png") }

    val bookIcon = remember { File("resources\\UI-QuestLog-BookIcon.png") }

    val titleFont = remember {
        TextStyle(
            fontFamily = FontFamily(
                Font(
                    resource = "font\\MORPHEUS.TTF",
                    style = FontStyle.Normal
                )
            ),
            fontSize = 18.sp,
        )
    }

    val descriptionStyle = remember {
        TextStyle(
            fontFamily = FontFamily(
                Font(
                    resource = "font\\FRIZQT__.TTF",
                    style = FontStyle.Normal
                )
            ),
            fontSize = 13.sp,
        )
    }

    MaterialTheme {
        Row {
            Column {
                Image(
                    bitmap = remember { Image.makeFromEncoded(topLeftImage.readBytes()).toComposeImageBitmap() },
                    ""
                )
                Image(
                    bitmap = remember { Image.makeFromEncoded(bottomLeftImage.readBytes()).toComposeImageBitmap() },
                    ""
                )
            }
            Column {
                Image(
                    bitmap = remember { Image.makeFromEncoded(topRightImage.readBytes()).toComposeImageBitmap() },
                    ""
                )
                Image(
                    bitmap = remember { Image.makeFromEncoded(bottomRightImage.readBytes()).toComposeImageBitmap() },
                    ""
                )
            }
        }

        Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = 82f
                    translationX = 31f
                },
        ) {
            TextField(
                textStyle = titleFont,
                value = "Wool Would Work",
                onValueChange = {},
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                )
            )
            TextField(
                textStyle = descriptionStyle,
                value = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                onValueChange = {},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                )
            )
        }
    }
}

fun main() = application {
    Window(
        undecorated = true,
        transparent = true,
        resizable = false,
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = with(LocalDensity.current) { 512.toDp() },
            height = with(LocalDensity.current) { 666.toDp() } // TODO figure out why 512 is bad
        ),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
