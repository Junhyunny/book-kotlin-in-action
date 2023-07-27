package blog.`in`.action.j

class LengthCounter {

    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}

fun main() {

    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi")
    println(lengthCounter.counter)
}