class Questificator {
    fun getQuestContainerList(): List<QuestContainer> {
        return listOf(
            QuestContainer(
                id = "_0",
                title = "Elwynn Forest",
                expanded = true,
                questList = listOf(
                    Quest(
                        id = "#0",
                        title = "Wool would work",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Standard,
                        selected = true
                    ),
                    Quest(
                        id = "#1",
                        title = "A Bundle of Trouble",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    ),
                    Quest(
                        id = "#2",
                        title = "Red Linen Goods",
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
                expanded = true,
                questList = listOf(
                    Quest(
                        id = "#5",
                        title = "Bounty on Murlocs",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Difficult,
                        selected = false
                    ),
                    Quest(
                        id = "#6",
                        title = "Cloth and Leather Armor",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.VeryDifficult,
                        selected = false
                    ),
                    Quest(
                        id = "#7",
                        title = "Desperate Prayer",
                        summary = "Gather 20 bundles of wool off the sheep in Elwynn Forest and bring them back to Julie Osworth.",
                        description = "Lorem ipsum etc, etc",
                        difficulty = QuestDifficulty.Impossible,
                        selected = false
                    )
                )
            )
        )
    }
}