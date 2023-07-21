package action.`in`.blog

fun <T> joinToString(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuffer(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun main() {

    val list = listOf(1, 2, 3)

    println(joinToString(list, ";", "(", ")"))

    println(joinToString(list, separator = ";", prefix = "(", postfix = ")"))

    println(joinToString(list))
}