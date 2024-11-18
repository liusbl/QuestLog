fun <T> List<T>.set(item: T, update: (T) -> T): List<T> {
    val index = this.indexOf(item)
    return this.toMutableList().apply {
        set(index, update(item))
    }
}