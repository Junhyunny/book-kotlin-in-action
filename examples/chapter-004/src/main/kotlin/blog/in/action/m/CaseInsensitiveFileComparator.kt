package blog.`in`.action.m

import java.io.File

object CaseInsensitiveFileComparator : Comparator<File> {

    override fun compare(f1: File, f2: File): Int {
        return f1.path.compareTo(f2.path, ignoreCase = true)
    }
}

data class Human(val name: String) {
    object NameComparator : Comparator<Human> {
        override fun compare(o1: Human, o2: Human): Int {
            return o1.name.compareTo(o2.name)
        }
    }
}

fun main() {

    println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user")))

    val files = listOf(File("/z"), File("/a"))
    println(files.sortedWith(CaseInsensitiveFileComparator))

    val humans = listOf(Human("Bob"), Human("Alice"))
    println(humans.sortedWith(Human.NameComparator))
    println(Human.NameComparator)
}