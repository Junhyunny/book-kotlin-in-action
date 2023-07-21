package action.`in`.blog

val set = hashSetOf(1, 2, 3, 7, 53)
val list = arrayListOf(1, 2, 3, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

fun main(args: Array<String>) {
    println(set)
    println(set.javaClass)

    println(list)
    println(list.javaClass)

    println(map)
    println(map.javaClass)

    println(set.max())
    println(set.min())
    println(set.last())

    println(list.max())
    println(list.min())
    println(list.last())
}