class Questificator {
    fun getQuestContainerList(): List<QuestContainer> {
        return listOf(
            QuestContainer(
                title = "Elwynn Forest",
                questList = listOf(
                    Quest(
                        title = "Wool would work",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult
                    ),
                    Quest(
                        title = "Wool would work #2",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult
                    ),
                    Quest(
                        title = "Wool would work #3",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult
                    )
                )
            ),
            QuestContainer(
                title = "Priest",
                questList = listOf(
                    Quest(
                        title = "Wool would work #4",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult
                    ),
                    Quest(
                        title = "Wool would work #5",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult
                    ),
                    Quest(
                        title = "Wool would work #6",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult
                    )
                )
            )
        )
    }
}