package action.`in`.blog.c

fun String.lastChar(): Char = this.get(this.length - 1)

fun main() {

    val message = "Hello World"

    println(message.lastChar())
}