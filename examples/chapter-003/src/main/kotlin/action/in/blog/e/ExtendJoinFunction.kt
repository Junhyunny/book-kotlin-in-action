package action.`in`.blog.e

fun <T> Collection<T>.customJoinToString(
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuffer(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun Collection<String>.join(
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
) = customJoinToString(separator, prefix, postfix)

fun main() {
    val list = listOf(1, 2, 3, 5, 6, 7, 100)
    println(list.customJoinToString(";", "(", ")"))

    val stringList = listOf("1", "2", "3")
    println(stringList.join())
}