
## Chapter 5. 람다로 프로그래밍

* 람다는 기본적으로 다른 함수에 넘길 수 있는 작은 코드 조각을 뜻한다.
* 람다를 사용하면 쉽게 공통 코드 구조를 라이브러리 함수로 뽑아낼 수 있다.

### 5.1. 람다 식과 멤버 참조

#### 5.1.1. 람다 소개: 코드 블록을 함수 인자로 넘기기

* 람다가 등장하기 전엔 무명 내부 클래스를 사용했다.
* 다만 무명 내부 클래스를 사용하여 코드를 함수에 넘기거나 변수에 저장하는 것은 상당히 번거로웠다.
* 함수형 프로그래밍에서는 함수를 값처럼 다루는 접근 방법을 택함으로써 이 문제를 해결했다.
* 람다 식을 사용하면 함수를 선언할 필요가 없고 코드 블록을 직접 함수의 인자로 전달할 수 있다.

#### 5.1.2. 람다와 컬렉션

* 자바 8 이전에는 컬렉션을 쉽게 처리하지 못 했다.
* 다음과 같은 코드를 람다식을 이용해 쉽게 처리할 수 있다.

```kotlin
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
}
```

* 람다식을 사용하면 다음과 같이 변경할 수 있다.

```kotlin
fun main() {

    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    findTheOldest(people) // Person(name=Bob, age=31)
    println(people.maxBy { person: Person -> person.age }) // Person(name=Bob, age=31)
    println(people.maxBy { it.age }) // Person(name=Bob, age=31)
}
```

#### 5.1.3. 람다 식의 문법

* 람다를 따로 선언해서 변수에 저장할 수도 있다.
* 함수에 인자로 넘기면서 바로 람다를 정의하는 경우가 대부분이다.
* 항상 중괄호 사이에 위치한다.
* 화살표 앞에는 파라미터, 화살표 뒤에는 분문이다.
* 인자 목록 주변에 괄호가 없다.
    * 순전히 화살표(->)가 인자 목록과 람다 본문을 구분한다.

```kotlin
{ x: Int, y: Int -> x + y }
```

* 람다 식은 변수에 저장하여 사용하거나 직접 호출할 수 있다.

```kotlin
fun main() {

    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 2)); // 3
    { println(42) }() // 42
}
```

* 람다식을 직접 호출하는 방법은 읽기 어렵고 쓸모가 없다.
* 코드의 일부분을 블록으로 둘러싸 실행할 필요가 있다면 run을 사용한다.

```kotlin
    run { println(42) }
```

* 실행 시점에 코틀린 람다 호출에는 아무런 부가 비용이 들지 않는다.
* 프로그램의 기본 구성 요소와 비슷한 성능을 낸다.

* 람다식은 메소드 인자에 이름을 통해 전달할 수도 있고, 바깥으로 뺄 수도 있다.

```kotlin
    val names1 = people.joinToString(separator = ", ", transform = { p: Person -> p.name })
    println(names1) // Alice, Bob

    val names2 = people.joinToString(", ") { p: Person -> p.name }
    println(names2) // Alice, Bob
```

* 람다 파라미터 타입을 제거할 수 있다.
* 파라미터 타입을 명시하거나 생략할 수 있다.
    * 파라미터 타입을 생략하는 경우 컴파일러가 추론한다.

```kotlin
    val people = listOf(Person("Alice", 29), Person("Bob", 31))

    println(people.maxBy { person: Person -> person.age }) // Person(name=Bob, age=31)
    println(people.maxBy { person -> person.age }) // Person(name=Bob, age=31)
```

* 람다의 파라미터 이름을 디폴트 이름인 `it`으로 변경하면 람다 식을 더 간단한게 작성할 수 있다.
    * 람다의 파라미터가 하나뿐이고 그 타입을 컴파일러가 추론할 수 있는 경우 `it`을 사용할 수 있다.
    * 람다 파라미터 이름을 따로 정하지 않은 경우에만 `it`이라는 이름이 자동으로 만들어진다.
* it을 사용하는 관습은 코드를 간결하게 만들어준다.
    * 하지만 남용하면 안된다.
    * 람다 안에 람다가 중첩되는 경우 람다의 파라미터를 명시하는 편이 낫다.
    * 파라미터를 명시하지 않으면 각각의 it이 가리키는 파라미터가 어떤 람다에 속했는지 파악하기 어렵다.

```kotlin
    println(people.maxBy { it.age }) // Person(name=Bob, age=31)
```

* 람다는 한 줄로만 이뤄지지 않는다.
* 본문이 여러 줄로 이뤄진 경우 본문 맨 마지막에 있는 식이 람다의 결과 값이 된다.

```kotlin
    val sumWithLog = {x: Int, y: Int ->
        println("Computing the sum of $x and $y")
        x + y
    }
    println(sumWithLog(1, 3)) // 4
```

#### 5.1.4. 현재 영역에 있는 변수에 접근

* 자바도 메소드 안에서 무명 내부 클래스를 정의하면 메소드의 로컬 변수를 무명 내부 클래스에서 사용할 수 있다.
* 람다를 함수 안에서 정의하면 함수의 파라미터뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 사용할 수 있다.


```kotlin
package action.`in`.kotlin.b

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
//    messages.forEach { item: String ->
//        println("$prefix $item")
//    }
    // 위 코드와 동일하게 동작합니다. 
    messages.forEach {
        println("$prefix $it")
    }
}

fun main() {

    val errors = listOf("403 Forbidden", "404 Not Found")

    printMessageWithPrefix(errors, "Error: ")
}
```

* 자바와 다른 점 중 중요한 내용
    * 코틀린 람다 안에서는 파이널 변수가 아니다.
    * 람다 안에서 바깥의 변수를 변경해도 된다.
* 람다 안에서 사용하는 외부 변수를 "람다가 포획(capture)한 변수"라고 부른다.
* 기본적으로 함수 안에 정의된 로컬 변수의 생명주기는 함수가 반환되면 끝난다.
    * 하지만 어떤 함수가 자신의 로컬 변수를 포획한 람다를 반환하거나 다른 변수에 저장한다면 로컬 변수의 생명주기와 함수의 생명주기가 달라진다.
    * 포획한 변수가 있는 람다를 저장해서 함수가 끝난 뒤에 실행해도 람다의 본문 코드는 여전히 포획한 변수를 읽거나 쓸 수 있다.
* 파이널 변수를 포획한 경우와 아닌 변수를 포획한 경우가 다르게 동작한다.
    * 파이널 변수를 포획한 경우 람다 코드를 변수 값과 함께 저장한다.
    * 일반 변수를 포획한 경우 변수를 특별한 래퍼로 감싸서 나중에 변경하거나 읽을 수 있게 만든 다음 래퍼에 대한 참조를 람다 코드와 함께 저장한다.

```kotlin
fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

fun main() {
    val responses = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
    printProblemCounts(responses)
}
```

#### 5.1.2. 멤버 참조

* 코틀린에서는 자바 8과 마찬가지로 함수를 값으로 바꿀 수 있다.
* 멤버 참조는 프로퍼티나 메소드를 단 하나만 호출하는 함수 값을 만든다. 

```kotlin
data class Person(
    val name: String,
    val age: Int
)

fun main() {
    // val getAge = { person: Person -> person.age }
    val getAge = Person::age
}
```

* 최상위에 선언된(그리고 다른 클래스의 멤버가 아닌) 함수나 프로퍼티를 참조할 수도 있다.

```kotlin
fun salute() = println("Salute!")
run(::salute)
```

* 인자가 여러 개인 다른 함수한테 작업을 위임하는 경우

```kotlin

fun sendEmail(person: Person, message: String) {}

fun main() {
    val action = { person: Person, message: String -> sendEmail(person, message) }
    val nextAction = ::sendEmail
}
```

* 생성자 참조를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다.

```kotlin
data class Person(
    val name: String,
    val age: Int
)

fun main() {
    val createPerson = ::Person
    val person = createPerson("Alice", 29)
    println(person) // Person(name=Alice, age=29)
}
```

* 확장 함수도 멤버 함수와 똑같은 방법으로 참조할 수 있다.

```kotlin
fun main() {
    fun Person.isAdult() = age >= 21
    val predicate = Person::isAdult
}
```

* 바운드 멤버 참조
    * 코틀린 1.0에서는 클래스의 메소드나 프로퍼티에 대한 참조를 얻은 다음에 그 참조를 호출할 때 항상 인스턴스 객체를 제공한다.
    * 코틀린 1.1부터는 바운드 멤버 참조(bound member reference)를 지원한다.
        * 바운드 멤버 참조를 사용하면 멤버 참조를 생성할 때 클래스 인스턴스를 함께 저장한 다음 나중에 그 인스턴스에 대해 멤버를 호출해준다.

```kotlin
fun main() {

    val p = Person("Dmitry", 34)
    val personAgeFunction = Person::age
    println(personAgeFunction(p))

    val dmitryAgeFunction = p::age
    println(dmitryAgeFunction())
}
```

### 5.2. 컬렉션 함수형 API

* 함수형 프로그래밍 스타일을 사용하면 컬렉션을 다룰 때 편리하다.

#### 5.2.1. 필수적인 함수: filter and map

* filter 함수는 컬렉션을 이터레이션하면서 주어진 람다에 각 원소를 넘겨서 결과가 true인 원소들만 모은다.
* 아래 코드는 짝수만 통과시킨다.
    * 원본 컬렉션의 데이터는 유지한다.

```kotlin
package action.`in`.kotlin.d

fun main() {
    val list = listOf(1, 2, 3, 4)
    println(list.filter { it % 2 == 0 })
    println(list)
}
```

* map 함수는 컬렉션에 담긴 데이터를 변환한다.
* 각 원소에 적용한 결과를 모아서 새 컬렉션을 만든다.
    * 원본 컬렉션의 데이터는 유지한다.

```kotlin
package action.`in`.kotlin.d

fun main() {
    val list = listOf(1, 2, 3, 4)
    println(list.map { it * it })
    println(list)
}
```

* 다음과 같은 예시를 만들 수 있다.
    * 사람 리스트를 이름 리스트로 변경하는 작업
    * 나이가 30세 이상인 사람의 이름을 출력하는 작업

```kotlin
fun main() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    println(people.map { it.name }) // ["Alice", "Bob"]
    println(people.map(Person::name)) // ["Alice", "Bob"]
    println(people.filter { it.age > 30 }.map(Person::name)) // ["Bob"]    
}
```

* 가장 나이가 많은 사람의 이름을 알고 싶은 경우 다음과 같이 코드를 작성할 수 있다.
    * 첫 번째 코드는 maxBy 연산을 계속적으로 수행한다.
    * 두 번째 코드는 연속적으로 수행되는 연산을 최소한으로 변경한 코드이다.
* 람다를 인자로 받는 함수에 람다를 넘기면 겉으로 볼 때는 단순해 보인다.
* 식의 내부 로직의 복잡도로 인해 실제로는 불합리한 계산식이 될 때가 있다.

```kotlin
fun main() {
    println(people.filter { it.age == people.maxBy(Person::age).age }) // 


    val maxAge = people.maxBy(Person::age).age
    println(people.filter { it.age == maxAge })
}
```
