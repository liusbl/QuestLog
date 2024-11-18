data class QuestContainer(
    override val id: String,
    val title: String,
    val path: String,
    val expanded: Boolean,
    val questList: List<Quest>,
) : Identifiable

fun List<QuestContainer>.setSummary(
    containerId: String,
    questId: String,
    summary: String
): List<QuestContainer> = set(containerId, questId, { quest -> quest.copy(summary = summary) })

fun List<QuestContainer>.setSelected(
    containerId: String,
    questId: String,
    selected: Boolean
): List<QuestContainer> = set(containerId, questId, { quest -> quest.copy(selected = selected) })

private fun List<QuestContainer>.set(containerId: String, questId: String, update: (Quest) -> Quest): List<QuestContainer> {
    val questContainer = this.first { it.id == containerId }
    val newQuestContainer = questContainer.set(questId, update)
    return this.set(questContainer, { newQuestContainer })
}

private fun QuestContainer.set(questId: String, update: (Quest) -> Quest): QuestContainer {
    val quest = questList.first { it.id == questId }
    val newQuestList = questList.set(quest, { update(quest) })
    return this.copy(questList = newQuestList)
}