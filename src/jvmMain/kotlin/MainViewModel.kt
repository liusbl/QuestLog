import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class MainViewModel(
    questificator: Questificator
) {
    private val initialQuestContainerList = questificator.getQuestContainerList()
    val questContainerList =
        mutableStateListOf<QuestContainer>().apply { addAll(initialQuestContainerList) }
    var currentQuest by mutableStateOf(initialQuestContainerList.flatMap { it.questList }.first { it.selected })

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

    fun onDescriptionChange(description: String) {
        val oldQuest = currentQuest
        currentQuest = currentQuest.copy(description = description)

        // TODO Only do after some ms. update debounce.
        updateList(oldQuest)
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
        println("newContainer: $newContainer")
        questContainerList[containerIndex] = newContainer
    }
}

fun List<Quest>.set(index: Int, item: Quest): List<Quest> = this.toMutableList().apply { set(index, item) }