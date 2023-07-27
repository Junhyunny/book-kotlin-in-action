package blog.`in`.action.g

import java.io.Serializable

interface State : Serializable
interface View {
    fun getCurrentState(): State
    fun restoreState(state: State)
}

class Button : View {

    override fun getCurrentState(): State {
        TODO("Not yet implemented")
    }

    override fun restoreState(state: State) {
        TODO("Not yet implemented")
    }

    class ButtonState : State
}

