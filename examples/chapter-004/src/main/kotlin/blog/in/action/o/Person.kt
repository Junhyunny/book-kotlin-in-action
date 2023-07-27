package blog.`in`.action.o

//class Person(val name: String) {
//    companion object Loader {
//        fun fromJSON(jsonText: String): Person = Person("junhyunny")
//    }
//}
//
//fun main() {
//    Person.Loader.fromJSON(("{name: 'junhyunny'}"))
//    Person.fromJSON(("{name: 'junhyunny'}"))
//}

//interface JSONFactory<T> {
//    fun fromJSON(jsonText: String): T
//}
//
//class Person(val name: String) {
//    companion object: JSONFactory<Person> {
//        override fun fromJSON(jsonText: String): Person {
//            TODO("Not yet implemented")
//        }
//    }
//}
//
//fun main() {
//    Person.fromJSON(("{name: 'junhyunny'}"))
//}

class Person(val firstName: String, val lastName: String) {
    companion object
}

fun Person.Companion.fromJSON(json: String): Person {
    TODO("Not yet implemented")
}