import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import org.jetbrains.skia.Image
import java.io.File

fun main() = application {
    val viewModel = MainViewModel(
        questificator = Questificator()
    )
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
        App(viewModel)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ApplicationScope.App(viewModel: MainViewModel) {
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

            QuestList(viewModel)

            QuestButtons()

            ScrollButtons()
            ScrollKnob(mousePosition)

            val quest = viewModel.currentQuest

            QuestText(
                title = quest.title,
                onTitleChange = viewModel::onTitleChange,
                summary = quest.summary,
                onSummaryChange = viewModel::onSummaryChange,
                description = quest.description,
                onDescriptionChange = viewModel::onDescriptionChange
            )
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
    description: String?,
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
        Box(modifier = Modifier.width(with(density) { 355.toDp() }))
        Column(
            modifier = Modifier.height(
                height = with(density) { 435.toDp() }
            )
        ) {
            Box(modifier = Modifier.height(with(density) { 75.toDp() }))
            BasicTextField(
                modifier = Modifier.width(with(density) { 270.toDp() }),
                textStyle = titleFont,
                value = title,
                onValueChange = onTitleChange,
            )
            BasicTextField(
                modifier = Modifier.width(with(density) { 270.toDp() }),
                textStyle = descriptionStyle,
                value = summary,
                onValueChange = onSummaryChange,
            )
            Spacer(modifier = Modifier.height(with(density) { 8.toDp() }))
            description?.let {
                BasicTextField(
                    modifier = Modifier.width(with(density) { 270.toDp() }),
                    textStyle = titleFont,
                    value = "Description",
                    onValueChange = { /* Do nothing */ },
                    readOnly = true,
                )
                BasicTextField(
                    modifier = Modifier.width(with(density) { 270.toDp() }),
                    textStyle = descriptionStyle,
                    value = description,
                    onValueChange = onDescriptionChange,
                )
            }
        }
    }
}

@Composable
private fun QuestList(viewModel: MainViewModel) {
    val minusButtonUp = remember { File("resources\\UI-MinusButton-Up.png") }
    val minusButtonDown = remember { File("resources\\UI-MinusButton-Down.png") }
    val plusButtonUp = remember { File("resources\\UI-PlusButton-Up.png") }
    val plusButtonDown = remember { File("resources\\UI-PlusButton-Down.png") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            items = viewModel.questContainerList.flatMap { container ->
                buildList {
                    add(container)
                    if (container.expanded) {
                        addAll(container.questList)
                    }
                }
            },
            key = { it.id }
        ) {
            when (it) {
                is QuestContainer -> {
                    val container = it
                    Box {
                        val interactionSource = remember { MutableInteractionSource() }
                        val isPressed by interactionSource.collectIsPressedAsState()

                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    translationY = 75f
                                    translationX = 22f
                                }
                                .clickable(interactionSource, indication = null) {
                                    viewModel.onExpandToggle(container)
                                }
                        ) {
                            if (isPressed) {
                                Image(
                                    bitmap = if (container.expanded) {
                                        remember {
                                            Image.makeFromEncoded(minusButtonDown.readBytes()).toComposeImageBitmap()
                                        }
                                    } else {
                                        remember {
                                            Image.makeFromEncoded(plusButtonDown.readBytes()).toComposeImageBitmap()
                                        }
                                    },
                                    contentDescription = ""
                                )
                            } else {
                                Image(
                                    bitmap = if (container.expanded) {
                                        remember {
                                            Image.makeFromEncoded(minusButtonUp.readBytes()).toComposeImageBitmap()
                                        }
                                    } else {
                                        remember {
                                            Image.makeFromEncoded(plusButtonUp.readBytes()).toComposeImageBitmap()
                                        }

                                    },
                                    contentDescription = ""
                                )
                            }
                        }
                        val textStyle = remember {
                            TextStyle(
                                fontFamily = FontFamily(
                                    Font(
                                        resource = "font\\FRIZQT__.TTF",
                                        style = FontStyle.Normal
                                    )
                                ),
                                fontSize = 11.sp,
                                color = QuestDifficulty.Header.color,
                                textAlign = TextAlign.Center
                            )
                        }

                        Text(
                            modifier = Modifier.graphicsLayer {
                                translationY = 75f
                                translationX = 40f
                            },
                            style = textStyle,
                            text = it.title
                        )

                    }

                }

                is Quest -> {
                    val textStyle = remember {
                        TextStyle(
                            fontFamily = FontFamily(
                                Font(
                                    resource = "font\\FRIZQT__.TTF",
                                    style = FontStyle.Normal
                                )
                            ),
                            fontSize = 11.sp,
                            color = it.difficulty.color,
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        modifier = Modifier.graphicsLayer {
                            translationY = 75f
                            translationX = 50f
                        },
                        style = textStyle,
                        text = it.title
                    )
                }
            }
        }
    }
}

@Composable
private fun QuestButtons() {
    val buttonUp = remember { File("resources\\UI-Panel-Button-Up.png") }
    val buttonDown = remember { File("resources\\UI-Panel-Button-Down.png") }

    val textStyle = remember {
        TextStyle(
            fontFamily = FontFamily(
                Font(
                    resource = "font\\FRIZQT__.TTF",
                    style = FontStyle.Normal
                )
            ),
            fontSize = 11.sp,
            color = Color(red = 0xd2, green = 0x97, blue = 0x33),
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun Button(
        title: String,
        translationX: Float,
        scaleX: Float,
        textTranslationX: Float,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationY = 436f
                    this.translationX = translationX
                }
                .clickable(interactionSource, indication = null) {
                    onClick()
                }
        ) {
            if (isPressed) {
                Image(
                    modifier = Modifier.scale(scaleX, 1f),
                    bitmap = remember { Image.makeFromEncoded(buttonDown.readBytes()).toComposeImageBitmap() },
                    contentDescription = ""
                )
            } else {
                Image(
                    modifier = Modifier.scale(scaleX, 1f),
                    bitmap = remember { Image.makeFromEncoded(buttonUp.readBytes()).toComposeImageBitmap() },
                    contentDescription = ""
                )
            }
            Text(
                modifier = Modifier.graphicsLayer {
                    translationY = 2.5f
                    this.translationX = textTranslationX
                },
                style = textStyle,
                text = title
            )
        }
    }

    val bigButtonScale = 1.57f

    Button(
        title = "Abandon Quest",
        translationX = 52f,
        scaleX = bigButtonScale,
        textTranslationX = -24f,
        onClick = { }
    )

    Button(
        title = "Share Quest",
        translationX = 176f,
        scaleX = bigButtonScale,
        textTranslationX = -12f,
        onClick = { }
    )

    Button(
        title = "Exit",
        translationX = 265f,
        scaleX = 1f,
        textTranslationX = 30f,
        onClick = { }
    )
}

// TODO Implement scroll buttons
@Composable
private fun ScrollButtons() {
    val buttonTopDisabled = remember { File("resources\\UI-ScrollBar-ScrollUpButton-Disabled.png") }
    val buttonTopDown = remember { File("resources\\UI-ScrollBar-ScrollUpButton-Down.png") }
    val buttonTopUp = remember { File("resources\\UI-ScrollBar-ScrollUpButton-Up.png") }

    val buttonBottomDisabled = remember { File("resources\\UI-ScrollBar-ScrollDownButton-Disabled.png") }
    val buttonBottomDown = remember { File("resources\\UI-ScrollBar-ScrollDownButton-Down.png") }
    val buttonBottomUp = remember { File("resources\\UI-ScrollBar-ScrollDownButton-Up.png") }

    Box(
        modifier = Modifier
            .graphicsLayer {
                translationY = scrollBarStartY - knobSize / 2 - 2
                translationX = knobTranslationX
            }
    ) {
        Image(
            bitmap = remember { Image.makeFromEncoded(buttonTopDisabled.readBytes()).toComposeImageBitmap() },
            contentDescription = ""
        )
    }

    Box(
        modifier = Modifier
            .graphicsLayer {
                translationY = scrollBarEndY + knobSize / 2 + 3
                translationX = knobTranslationX
            }
    ) {
        Image(
            bitmap = remember { Image.makeFromEncoded(buttonBottomDisabled.readBytes()).toComposeImageBitmap() },
            contentDescription = ""
        )
    }

}

val knobSize = 32f
val scrollBarStartY = 84f
val scrollBarEndY = 412f
val knobTranslationX = 646f

@Composable
private fun ScrollKnob(mousePosition: Pair<Offset, Boolean>) {
    val scrollKnob = remember { File("resources\\UI-ScrollBar-Knob.png") } // 32x32

    var knobTranslationY by remember { mutableStateOf(scrollBarStartY) }
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
