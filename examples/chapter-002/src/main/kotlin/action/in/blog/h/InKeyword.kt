package action.`in`.blog.h

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'
fun recognize(c: Char) =
    when (c) {
        in '0'..'9' -> "It is a digit"
        in 'a'..'z', in 'A'..'Z' -> "It is a letter"
        else -> "I don't know"
    }

fun main(args: Array<String>) {

    println(isLetter('A')) // true
    println(isLetter('f')) // true
    println(isNotDigit('v')) // true
    println(isNotDigit('0')) // false

    println(recognize('A'))
    println(recognize('f'))
    println(recognize('v'))
    println(recognize('0'))

    println("Kotlin" in "Java".."Scala") // true
    println("Kotlin" in setOf("Java", "Scala")) // flase
}