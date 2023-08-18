package action.`in`.kotlin.d

data class Person(
    val name: String,
    val age: Int
)

data class Book(
    val title: String,
    val authors: List<String>
)

fun main() {
    val list = listOf(1, 2, 3, 4)
    println(list.filter { it % 2 == 0 })
    println(list)

    println(list.map { it * it })
    println(list)

    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    println(people.map { it.name })
    println(people.map(Person::name))
    println(people.filter { it.age > 30 }.map(Person::name))

    println(people.filter { it.age == people.maxBy(Person::age).age })

    val maxAge = people.maxBy(Person::age).age
    println(people.filter { it.age == maxAge })

    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.uppercase() })

    val numberList = listOf(1, 2, 3, 27, 31, 25)
    println(numberList.all { it == 3 })
    println(numberList.any { it != 3 })
    println(numberList.all { it <= 27 })
    println(numberList.any { it <= 27 })
    println(numberList.count { it <= 27 })
    println(numberList.find { it >= 27 })
    println(numberList.find { it > 31 })
    println(numberList.firstOrNull { it < 31 })
    println(numberList.firstOrNull { it > 31 })

    val needGroupingPeople = listOf(Person("Alice", 29), Person("Bob", 31), Person("Carol", 31))
    println(needGroupingPeople.groupBy { it.age })

    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })
    println(strings.map { it.toList() }.flatten())

    val books = listOf(
        Book("Thursday Next", listOf("Jasper Fforde")),
        Book("Mort", listOf("Terry Pratchett")),
        Book("Good Omens", listOf("Terry Pratchett", "Neil Gainman"))
    )
    println(books.flatMap { it.authors }.toSet())

    val values = listOf("ab", "az", "b", "ca", "acw")
    println(
        values
            .map { it.uppercase() } // temporal collection
            .filter { it.startsWith("A") } // temporal collection
    )
    println(
        values
            .asSequence() // origin collection to sequence
            .map { it.uppercase() } // no temporal collection
            .filter { it.startsWith("A") } // no temporal collection
            .toList()
    )

    listOf(1, 2, 3, 4, 5).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }

    listOf(1, 2, 3, 4, 5).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList()

    println()

    listOf(1, 2, 3, 4, 5)
        .map { print("map($it) "); it * it }
        .find { print("find($it) "); it > 5 }

    println()

    listOf(1, 2, 3, 4, 5).asSequence()
        .map { print("map($it) "); it * it }
        .find { print("find($it) "); it > 5 }

    println()

    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())
}
