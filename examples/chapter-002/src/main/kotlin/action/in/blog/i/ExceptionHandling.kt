package action.`in`.blog.i

import java.io.BufferedReader
import java.io.StringReader

fun getPercentage(number: Int) =
    if (number in 0..100) number
    else throw IllegalArgumentException("A percentage value must be between 0 and 100: $number")

fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (exception: NumberFormatException) {
        println("exception message: ${exception.message}")
        0
        // return
    }
    println(number)
}

fun main(args: Array<String>) {
    try {
        val percentage = getPercentage(111)
        println("$percentage")
    } catch (exception: RuntimeException) {
        println("exception message: ${exception.message}")
    } finally {
        println("finally")
    }
    readNumber(BufferedReader(StringReader("Hello World")))
}