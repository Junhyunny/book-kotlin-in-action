package action.`in`.blog.b

class Person(
    val name: String, // 읽기 전용 프로퍼티, 코틀린은 비공개 필드와 필드를 읽는 단순한 공개 게터(getter)를 만든다.
    var isMarried: Boolean // 쓸 수 있는 프로퍼티로 비공개 필드와 공개 게터, 공개 세터(setter)를 만든다.
)

fun main(args: Array<String>) {

    val person = Person("Bob", true)
    println(person.name)
    println(person.isMarried)

    person.isMarried = false
    println(person.isMarried)
}
