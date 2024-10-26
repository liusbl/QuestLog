sealed interface Feature : Todo {
    object Description : Feature
}

interface Todo {
    val todo: String
        get()= "TODO"
}