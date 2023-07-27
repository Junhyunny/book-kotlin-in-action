package blog.`in`.action.a

interface Focusable {

    fun setFocus(boolean: Boolean) {
        println("I ${if (boolean) "got" else "lost"} focus.")
    }

    fun showOff() = println("I am Focusable show off")
}