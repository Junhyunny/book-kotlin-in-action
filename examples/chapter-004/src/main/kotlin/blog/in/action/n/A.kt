package blog.`in`.action.n

class A {
    companion object {
        fun bar() {
            println("companion object called")
        }
    }
}

fun main() {
    A.bar() // companion object called
}