data class QuestContainer(
    val containerId: QuestContainerId,
    val title: String,
    val path: String,
    val expanded: Boolean,
    val questList: List<Quest>,
) : Identifiable by containerId

@JvmInline
value class QuestContainerId(override val id: String) : Identifiable

fun List<QuestContainer>.setSummary(
    containerId: QuestContainerId,
    questId: QuestId,
    summary: String
): List<QuestContainer> = set(containerId, questId, { quest -> quest.copy(summary = summary) })

fun List<QuestContainer>.setSelected(
    containerId: QuestContainerId,
    questId: QuestId,
    selected: Boolean
): List<QuestContainer> = set(containerId, questId, { quest -> quest.copy(selected = selected) })

fun List<QuestContainer>.setScrollRatio(
    containerId: QuestContainerId,
    questId: QuestId,
    scrollRatio: Float
): List<QuestContainer> = set(containerId, questId, { quest -> quest.copy(scrollRatio = scrollRatio) })

private fun List<QuestContainer>.set(
    containerId: QuestContainerId,
    questId: QuestId,
    update: (Quest) -> Quest
): List<QuestContainer> {
    val questContainer = this.first { it.containerId == containerId }
    val newQuestContainer = questContainer.set(questId, update)
    return this.set(questContainer, { newQuestContainer })
}

private fun QuestContainer.set(questId: QuestId, update: (Quest) -> Quest): QuestContainer {
    val quest = questList.first { it.questId == questId }
    val newQuestList = questList.set(quest, { update(quest) })
    return this.copy(questList = newQuestList)
}