package action.`in`.blog.override

open class View {

    open fun click() = println("View Clicked")
}

class Button : View() {
    override fun click() = println("Button Clicked")
}

fun View.shutoff() = println("I am a view")
fun Button.shutoff() = println("I am a button")

fun main() {

    val button = Button()
    button.click()
    button.shutoff()

    val view: View = Button()
    view.click()
    view.shutoff()
}