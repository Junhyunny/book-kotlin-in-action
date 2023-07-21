package action.`in`.blog.extend

fun String.lastChar(): Char = this.get(this.length - 1)

fun main() {

    val message = "Hello World"

    println(message.lastChar())
}