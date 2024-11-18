import java.io.File

class Questificator {
    fun getQuestContainerList(): List<QuestContainer> =
        File("Quests").walkTopDown()
            .drop(1)
            .foldIndexed<File, List<QuestContainer>>(emptyList()) { index, acc, file ->
                if (file.isDirectory) {
                    acc + listOf(
                        QuestContainer(
                            containerId = QuestContainerId("_$index"),
                            title = file.name,
                            path = file.path,
                            expanded = true,
                            questList = emptyList(),
                        )
                    )
                } else {
                    val (parameters, summary, description) = file.readText().split("--------").map(String::trim)
                    val parameterMap = parameters.lines().associate {
                        val (key, value) = it.split(":")
                        key to value
                    }
                    val quest = Quest(
                        questId = QuestId(parameterMap["id"] ?: "#$index"),
                        title = file.nameWithoutExtension,
                        summary = summary + "\n" + description,
                        //                        description = description,
                        difficulty = QuestDifficulty.valueOf(parameterMap["difficulty"]!!),
                        selected = parameterMap["selected"].toBoolean(),
                        scrollRatio = parameterMap["scrollRatio"]?.toFloat() ?: 0f
                    )

                    val lastContainer = acc.last().run {
                        copy(questList = questList + listOf(quest))
                    }
                    acc.dropLast(1) + listOf(lastContainer)
                }
            }
            .sortedBy { it.title }
            .map { container ->
                container.copy(questList = container.questList.sortedWith(
                    compareByDescending<Quest> { it.difficulty }.thenBy { it.title })
                )
            }
}