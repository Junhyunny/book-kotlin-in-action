package action.`in`.blog

import java.util.*

val binaryTree = TreeMap<Char, String>()

fun main(args: Array<String>) {

    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.code)
        binaryTree[c] = binary
    }

    for ((letter, binary) in binaryTree) {
        println("$letter = $binary")
    }

    for ((key) in binaryTree) {
        println("$key = ${binaryTree[key]}")
    }

    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("${index}: $element")
    }
}