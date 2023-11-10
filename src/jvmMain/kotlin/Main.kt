import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import org.jetbrains.skia.Image
import java.io.File

@Composable
@Preview
fun ApplicationScope.App() {
    val leftImage = remember { File("resources\\UI-QuestLog-Left.png") } // 512x512
    val rightImage = remember { File("resources\\UI-QuestLog-Right.png") } // 256x512

    val bookIcon = remember { File("resources\\UI-QuestLog-BookIcon.png") }

    val closeButtonUp = remember { File("resources\\UI-Panel-MinimizeButton-Up.png") }
    val closeButtonDown = remember { File("resources\\UI-Panel-MinimizeButton-Down.png") }

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
            Image(
                bitmap = remember { Image.makeFromEncoded(leftImage.readBytes()).toComposeImageBitmap() },
                ""
            )
            Image(
                bitmap = remember { Image.makeFromEncoded(rightImage.readBytes()).toComposeImageBitmap() },
                ""
            )
        }

        Image(
            modifier = Modifier
                .graphicsLayer {
                    translationY = 3.8f
                    translationX = 6.8f
                },
            bitmap = remember { Image.makeFromEncoded(bookIcon.readBytes()).toComposeImageBitmap() },
            contentDescription = ""
        )
        val interactionSource = remember { MutableInteractionSource() }
        val isClosePressed by interactionSource.collectIsPressedAsState()

        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationY = 8f
                    translationX = 650f
                }
                .clickable(interactionSource, indication = null) { exitApplication() }
        ) {
            if (isClosePressed) {
                Image(
                    bitmap = remember { Image.makeFromEncoded(closeButtonDown.readBytes()).toComposeImageBitmap() },
                    contentDescription = ""
                )
            } else {
                Image(
                    bitmap = remember { Image.makeFromEncoded(closeButtonUp.readBytes()).toComposeImageBitmap() },
                    contentDescription = ""
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
            width = with(LocalDensity.current) { 1000.toDp() },
            height = with(LocalDensity.current) { 1000.toDp() } // TODO figure out why 512 is bad
        ),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
