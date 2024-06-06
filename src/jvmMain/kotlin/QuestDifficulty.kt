import androidx.compose.ui.graphics.Color

enum class QuestDifficulty(val color: Color) {
    Impossible(Color(red = 1.00f, green = 0.10f, blue = 0.10f)),
    VeryDifficult(Color(red = 1.00f, green = 0.50f, blue = 0.25f)),
    Difficult(Color(red = 1.00f, green = 1.00f, blue = 0.00f)),
    Standard(Color(red = 0.25f, green = 0.75f, blue = 0.25f)),
    Trivial(Color(red = 0.50f, green = 0.50f, blue = 0.50f)),
    Header(Color(red = 0.7f, green = 0.7f, blue = 0.7f))
}
