data class QuestContainer(
    override val id: String,
    val title: String,
    val expanded: Boolean,
    val questList: List<Quest>,
): Identifiable