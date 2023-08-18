package action.`in`.kotlin.e

fun postponeComputation(delay: Int, computation: Runnable) {}

val runnable = Runnable { println(42) }

fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

fun main() {
    postponeComputation(1000) { println(42) }

    postponeComputation(1000, runnable)

    createAllDoneRunnable().run()
}