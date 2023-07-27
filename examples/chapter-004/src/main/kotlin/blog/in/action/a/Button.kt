package blog.`in`.action.a

class Button : Clickable, Focusable {

    override fun click() {
        println("I was clicked")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
        println("I am show off in Button")
    }
}

fun main() {
    val button = Button()
    button.click()
    button.showOff()
    button.setFocus(false)
}

