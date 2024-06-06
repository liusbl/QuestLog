import androidx.compose.ui.graphics.Color

data class Quest(
    override val id: String,
    val title: String,
    val summary: String,
    val description: String?,
    val difficulty: QuestDifficulty,
    val selected: Boolean
): Identifiable
