data class QuestContainer(
    override val id: String,
    val title: String,
    val path: String,
    val expanded: Boolean,
    val questList: List<Quest>,
) : Identifiable

fun QuestContainer.setSummary(
    questId: String,
    summary: String
): QuestContainer {
    val quest = questList.first { it.id == questId }
    val newQuestList = questList.set(quest, { quest.copy(summary = summary) })
    return this.copy(questList = newQuestList)
}

fun List<QuestContainer>.setSummary(
    containerId: String,
    questId: String,
    summary: String
): List<QuestContainer> {
    val questContainer = this.first { it.id == containerId }
    val newQuestContainer = questContainer.setSummary(questId, summary)
    return this.set(questContainer, { newQuestContainer })
}