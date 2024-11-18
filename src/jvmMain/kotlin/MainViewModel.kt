import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList


class MainViewModel(
    questificator: Questificator
) {
    private val initialQuestContainerList = questificator.getQuestContainerList()
    val questContainerList =
        mutableStateListOf<QuestContainer>().apply { addAll(initialQuestContainerList) }
    var currentQuest by mutableStateOf(initialQuestContainerList.flatMap { it.questList }.first { it.selected })
//    private var currentContainerId = questContainerList.first { it.questList.contains(currentQuest) }.containerId

    fun onTitleChange(title: String) {
        val oldQuest = currentQuest
        currentQuest = currentQuest.copy(title = title)

        // TODO Only do after some ms. update debounce.
        updateList(oldQuest)
    }

    fun onSummaryChange(summary: String) {
        val oldQuest = currentQuest
        currentQuest = currentQuest.copy(summary = summary)

        // TODO Only do after some ms. update debounce.
        updateList(oldQuest)
    }

//    fun onDescriptionChange(description: String) {
//        val oldQuest = currentQuest
//        currentQuest = currentQuest.copy(description = description)
//
//        // TODO Only do after some ms. update debounce.
//        updateList(oldQuest)
//    }

    fun onExpandToggle(it: QuestContainer) {
        questContainerList.update(it) { copy(expanded = !expanded) }
    }

    private fun updateList(oldQuest: Quest) {
        val (containerIndex, questIndex) = questContainerList.withIndex()
            .firstNotNullOf { (containerIndex, container) ->
                val questIndex = container.questList.indexOfFirst { it == oldQuest }
                if (questIndex != -1) {
                    containerIndex to questIndex
                } else {
                    null
                }
            }

        val container = questContainerList[containerIndex]
        val newContainer = container.copy(questList = container.questList.set(questIndex, currentQuest))
        questContainerList[containerIndex] = newContainer
    }

    fun onQuestClick(quest: Quest) {
        if (quest == currentQuest) return

        val oldQuest = currentQuest
        val updatedOldQuest = oldQuest.copy(selected = false)
        val updatedNewQuest = quest.copy(selected = true)
        currentQuest = updatedNewQuest

        val (oldContainerIndex, oldQuestIndex) = questContainerList.withIndex()
            .firstNotNullOf { (containerIndex, container) ->
                val questIndex = container.questList.indexOfFirst { it == oldQuest }
                if (questIndex != -1) {
                    containerIndex to questIndex
                } else {
                    null
                }
            }

        val (newContainerIndex, newQuestIndex) = questContainerList.withIndex()
            .firstNotNullOf { (containerIndex, container) ->
                val questIndex = container.questList.indexOfFirst { it == quest }
                if (questIndex != -1) {
                    containerIndex to questIndex
                } else {
                    null
                }
            }

        val oldContainer = questContainerList[oldContainerIndex]
        val updatedContainer = oldContainer.copy(questList = oldContainer.questList.set(oldQuestIndex, updatedOldQuest))
        questContainerList[oldContainerIndex] = updatedContainer

        val newContainer = questContainerList[newContainerIndex]
        val moreUpdatedContainer =
            newContainer.copy(questList = newContainer.questList.set(newQuestIndex, updatedNewQuest))
        questContainerList[newContainerIndex] = moreUpdatedContainer
    }

    fun onScrollRatioSet(ratio: Float) {
        // TODO doesn't quite work
        val oldQuest = currentQuest.copy()
        currentQuest = currentQuest.copy(scrollRatio = ratio)
        val currentContainerId = questContainerList.first { it.questList.contains(oldQuest) }.containerId
        val newList = questContainerList.setScrollRatio(currentContainerId, currentQuest.questId, ratio)
        questContainerList.set(newList)
    }
}

fun List<Quest>.set(index: Int, item: Quest): List<Quest> = this.toMutableList().apply { set(index, item) }

fun SnapshotStateList<QuestContainer>.update(
    oldItem: QuestContainer,
    update: QuestContainer.() -> QuestContainer
) {
    val index = this.indexOf(oldItem)
    this[index] = oldItem.update()
}

fun SnapshotStateList<QuestContainer>.set(
    newContainerList: List<QuestContainer>
) {
    newContainerList.forEachIndexed { index, newQuestContainer ->
        this[index] = newQuestContainer
    }
}