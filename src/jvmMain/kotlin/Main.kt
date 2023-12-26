import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import org.jetbrains.skia.Image
import java.io.File

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ApplicationScope.App() {
    var mousePosition by remember { mutableStateOf(Offset(0f, 0f) to false) }
    MaterialTheme {
        Box(
            modifier = Modifier.onPointerEvent(PointerEventType.Move) {
                val change = it.changes.first()
                mousePosition = change.position to change.pressed
            }
        ) {
            Background()
            BookIcon()
            CloseButton(onCloseClick = ::exitApplication)

            var title by remember { mutableStateOf("Wool Would Work") }
            var summary by remember { mutableStateOf("Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.") }
            var description by remember { mutableStateOf("Lorem ipsum etc etc") }
            QuestText(
                title = title,
                onTitleChange = { title = it },
                summary = summary,
                onSummaryChange = { summary = it },
                description = description,
                onDescriptionChange = { description = it }
            )
            ScrollKnob(mousePosition)
        }
    }
}

@Composable
private fun Background() {
    val leftImage = remember { File("resources\\UI-QuestLog-Left.png") } // 512x512
    val rightImage = remember { File("resources\\UI-QuestLog-Right.png") } // 256x512
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
}

@Composable
private fun BookIcon() {
    val bookIcon = remember { File("resources\\UI-QuestLog-BookIcon.png") }
    Image(
        modifier = Modifier
            .graphicsLayer {
                translationY = 3.5f
                translationX = 6.5f
            },
        bitmap = remember { Image.makeFromEncoded(bookIcon.readBytes()).toComposeImageBitmap() },
        contentDescription = ""
    )
}

@Composable
private fun CloseButton(
    onCloseClick: () -> Unit
) {
    val closeButtonUp = remember { File("resources\\UI-Panel-MinimizeButton-Up.png") }
    val closeButtonDown = remember { File("resources\\UI-Panel-MinimizeButton-Down.png") }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .graphicsLayer {
                translationY = 8f
                translationX = 650f
            }
            .clickable(interactionSource, indication = null) { onCloseClick() }
    ) {
        if (isPressed) {
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
}

@Composable
private fun QuestText(
    title: String,
    onTitleChange: (String) -> Unit,
    summary: String,
    onSummaryChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit
) {
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

    val density = LocalDensity.current
    Row {
        Box(modifier = Modifier.width(with(density) { 335.toDp() }))
        Column {
            Box(modifier = Modifier.height(with(density) { 55.toDp() }))
            val colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            )
            TextField(
                modifier = Modifier.width(with(density) { 310.toDp() }),
                textStyle = titleFont,
                value = title,
                onValueChange = onTitleChange,
                colors = colors
            )
            TextField(
                modifier = Modifier.width(with(density) { 310.toDp() }),
                textStyle = descriptionStyle,
                value = summary,
                onValueChange = onSummaryChange,
                colors = colors
            )
            TextField(
                modifier = Modifier.width(with(density) { 310.toDp() }),
                textStyle = titleFont,
                value = "Description",
                onValueChange = { /* Do nothing */ },
                readOnly = true,
                colors = colors
            )
            TextField(
                modifier = Modifier.width(with(density) { 310.toDp() }),
                textStyle = descriptionStyle,
                value = description,
                onValueChange = onDescriptionChange,
                colors = colors
            )
        }
    }
}

// TODO Implement scroll buttons
@Composable
private fun ScrollButtons() {
    val buttonTopDisabled = remember { File("resources\\UUI-ScrollBar-ScrollUpButton-Disabled.png") }
    val buttonTopDown = remember { File("resources\\UUI-ScrollBar-ScrollUpButton-Down.png") }
    val buttonTopUp = remember { File("resources\\UUI-ScrollBar-ScrollUpButton-Up.png") }

    val buttonBottomDisabled = remember { File("resources\\UUI-ScrollBar-ScrollDownButton-Disabled.png") }
    val buttonBottomDown = remember { File("resources\\UUI-ScrollBar-ScrollDownButton-Down.png") }
    val buttonBottomUp = remember { File("resources\\UUI-ScrollBar-ScrollDownButton-Up.png") }
}

@Composable
private fun ScrollKnob(mousePosition: Pair<Offset, Boolean>) {
    val scrollKnob = remember { File("resources\\UI-ScrollBar-Knob.png") } // 32x32
    val knobSize = 32f
    val scrollBarStartY = 84f
    val scrollBarEndY = 412f
    var knobTranslationY by remember { mutableStateOf(scrollBarStartY) }
    val knobTranslationX = 646f
    val position = mousePosition.first
    val pressed = mousePosition.second

    val xKnobMax = knobTranslationX + knobSize // by remember { derivedStateOf {
    val xKnobMin = knobTranslationX // by remember { derivedStateOf {
    val yKnobMax = knobTranslationY + knobSize // by remember { derivedStateOf {
    val yKnobMin = knobTranslationY // by remember { derivedStateOf {
    var interactionStarted by remember { mutableStateOf(false) }


//    println("TESTING pressed: $pressed, interactionStarted: $interactionStarted, knobTranslationY: $knobTranslationY, " +
//            "xKnobMax: $xKnobMax, xKnobMin: $xKnobMin, yKnobMax: $yKnobMax, yKnobMin: $yKnobMin")
    if (pressed && interactionStarted) {
        // middle of interaction
        knobTranslationY = (position.y - knobSize / 2).coerceIn(scrollBarStartY, scrollBarEndY)
    } else if (pressed && position.x > xKnobMin && position.x < xKnobMax && position.y > yKnobMin && position.y < yKnobMax) {
        // first time
        interactionStarted = true
        knobTranslationY = (position.y - knobSize / 2).coerceIn(scrollBarStartY, scrollBarEndY)
    } else if (!pressed) {
        interactionStarted = false
    }
    Box(
        modifier = Modifier
            .graphicsLayer {
                translationY = knobTranslationY
                translationX = knobTranslationX
            }
    ) {
        Image(
            bitmap = remember { Image.makeFromEncoded(scrollKnob.readBytes()).toComposeImageBitmap() },
            contentDescription = ""
        )
    }
}
