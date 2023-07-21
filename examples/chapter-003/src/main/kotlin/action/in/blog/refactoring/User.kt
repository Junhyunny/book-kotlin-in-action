package action.`in`.blog.refactoring

class User(
    val id: Int, val name: String, val address: String
) {

    private fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${this.id}: empty $fieldName")
        }
    }

    fun validate() {
        validate(name, "name")
        validate(address, "address")
    }
}

fun saveUser(user: User) {
    user.validate()
    println("Save user data")
}

fun main() {
    saveUser(User(1, "", "World"))
}