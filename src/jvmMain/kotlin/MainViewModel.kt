import androidx.compose.runtime.*

class MainViewModel(
    questificator: Questificator
) {
    val questContainerList =
        mutableStateListOf<QuestContainer>().apply { addAll(questificator.getQuestContainerList()) }
    var currentQuest by mutableStateOf(questContainerList.flatMap { it.questList }.first { it.selected })

    fun onTitleChange(title: String) {
        currentQuest = currentQuest.copy(title = title)
    }

    fun onSummaryChange(summary: String) {
        currentQuest = currentQuest.copy(summary = summary)
    }

    fun onDescriptionChange(description: String) {
        currentQuest = currentQuest.copy(description = description)
    }
}