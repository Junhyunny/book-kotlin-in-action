package action.`in`.kotlin.c

data class Person(
    val name: String,
    val age: Int
)

fun sendEmail(person: Person, message: String) {}

fun salute() = println("Salute!")

fun main() {

    run(::salute)

    val action = { person: Person, message: String -> sendEmail(person, message) }
    val nextAction = ::sendEmail

    val createPerson = ::Person
    val person = createPerson("Alice", 29)
    println(person)

    fun Person.isAdult() = age >= 21
    val predicate = Person::isAdult

    val p = Person("Dmitry", 34)
    val personAgeFunction = Person::age
    println(personAgeFunction(p))

    val dmitryAgeFunction = p::age
    println(dmitryAgeFunction())
}