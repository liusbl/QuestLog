import androidx.compose.ui.graphics.Color

data class Quest(
    val title: String,
    val summary: String,
    val description: String?,
    val difficulty: QuestDifficulty,
)
