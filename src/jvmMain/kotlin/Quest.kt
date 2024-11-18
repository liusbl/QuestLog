data class Quest(
    val questId: QuestId,
    val title: String,
    val summary: String,
//    val description: String?,
    val difficulty: QuestDifficulty,
    val selected: Boolean,
    val scrollRatio: Float
) : Identifiable by questId

@JvmInline
value class QuestId(override val id: String) : Identifiable