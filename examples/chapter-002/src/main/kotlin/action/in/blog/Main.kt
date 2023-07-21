package action.`in`.blog

import java.util.*

fun main(args: Array<String>) {
    println("Hello ${if (args.isNotEmpty()) args[0] else "Kotlin"}")

    println(max(1, 2))

    val message: String = if (canPerformOperation()) "Success" else "Fail"
    // compile error
    // message = "Hello World"
    println(message)
}

fun canPerformOperation(): Boolean = Random().nextBoolean()


fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

//fun max(a: Int, b: Int): Int = if (a > b) a else b

//fun max(a: Int, b: Int) = if (a > b) a else b
