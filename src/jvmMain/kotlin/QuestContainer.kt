data class QuestContainer(
    override val id: String,
    val title: String,
    val questList: List<Quest>
): Identifiable