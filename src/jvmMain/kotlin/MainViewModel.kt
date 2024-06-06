import androidx.compose.runtime.mutableStateListOf

class MainViewModel(
    questificator: Questificator
) {
    val questContainerList = mutableStateListOf<QuestContainer>()

    init {
        questContainerList.addAll(questificator.getQuestContainerList())
    }
}