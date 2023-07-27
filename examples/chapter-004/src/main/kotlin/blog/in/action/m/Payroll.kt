package blog.`in`.action.m

class Person

object Payroll {

    val allEmployees = arrayListOf<Person>()

    fun calculateSalary() {
        for (person in allEmployees) {
            // ...
        }
    }
}

fun main() {

    Payroll.allEmployees.add(Person())
    Payroll.calculateSalary()
}