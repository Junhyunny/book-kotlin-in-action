package action.`in`.blog

fun fizzBuzz(number: Int) =
    when {
        number % 15 == 0 -> "FizzBuzz"
        number % 5 == 0 -> "Buzz"
        number % 3 == 0 -> "Fizz"
        else -> "${number}"
    }

fun main(args: Array<String>) {

    for (index in 1..100) {
        println(fizzBuzz(index))
    }

    for (index in 100 downTo 1 step 2) {
        println(fizzBuzz(index))
    }

    for (index in 0 until 100) {
        println(fizzBuzz(index))
    }
}