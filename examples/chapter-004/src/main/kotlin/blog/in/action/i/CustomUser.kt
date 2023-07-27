package blog.`in`.action.i

class CustomUser(val name: String) {

    var address: String = "unspecified"
        set(value: String) {
            println(
                """
                Address was changed for $name
                "$field" -> "$value"
            """.trimIndent()
            )
            field = value
        }
}

fun main() {

    val user = CustomUser("Alice")
    user.address = "Elsenheimerstrasse 47,80687 Muenchen"
}