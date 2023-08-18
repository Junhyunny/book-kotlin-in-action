package action.`in`.kotlin.a

data class Person(
    val name: String,
    val age: Int
)

fun findTheOldest(people: List<Person>) {
    var maxAge = 0
    var theOldest: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun main() {

    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    findTheOldest(people)
    println(people.maxBy { person: Person -> person.age })
    println(people.maxBy { person -> person.age })
    println(people.maxBy { it.age })

    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 2));

    { println(42) }()
    run { println(42) }

    val names1 = people.joinToString(separator = ", ", transform = { p: Person -> p.name })
    println(names1) // Alice, Bob

    val names2 = people.joinToString(", ") { p: Person -> p.name }
    println(names2) // Alice, Bob

    val sumWithLog = {x: Int, y: Int ->
        println("Computing the sum of $x and $y")
        x + y
    }
    println(sumWithLog(1, 3))
}