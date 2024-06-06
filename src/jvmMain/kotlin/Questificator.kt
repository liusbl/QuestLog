class Questificator {
    fun getQuestContainerList(): List<QuestContainer> {
        return listOf(
            QuestContainer(
                id = "_0",
                title = "Elwynn Forest",
                questList = listOf(
                    Quest(
                        id = "#0",
                        title = "Wool would work #0",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = true
                    ),
                    Quest(
                        id = "#1",
                        title = "Wool would work #1",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    ),
                    Quest(
                        id = "#2",
                        title = "Wool would work #2",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    )
                )
            ),
            QuestContainer(
                id = "_1",
                title = "Priest",
                questList = listOf(
                    Quest(
                        id = "#5",
                        title = "Wool would work #5",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    ),
                    Quest(
                        id = "#6",
                        title = "Wool would work #6",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    ),
                    Quest(
                        id = "#7",
                        title = "Wool would work #7",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    )
                )
            )
        )
    }
}