package action.`in`.blog.extend

val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }

fun main() {

    println(mapOf(1 to "one"))
    println("This is fake".lastChar)

    val builder = StringBuilder("This is banana")
    builder.lastChar = 'A'
    println(builder.toString())
}