
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
    println(people.filter { it.age == people.maxBy(Person::age).age }) // 리스트에 들어있는 아이템 수만큼 maxBy 연산

    val maxAge = people.maxBy(Person::age).age // better code
    println(people.filter { it.age == maxAge })
}
```

* 필터와 변환 함수를 맵에 적용할 수 있다.

```kotlin
fun main() {
    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.uppercase() })
}
```

#### 5.2.2. all, any, count, find: 컬렉션에 술어 적용

* 컬렉션에 대해 자주 수행하는 연산으로 컬렉션의 모든 원소가 어떤 조건을 만족하는지 판단하는 연산이 있다.
* find 함수는 첫 번째 아이템을 찾는다.
    * 찾지 못하는 경우 null을 반환한다.
    * firstOrNull 함수를 사용하는 것이 더 명시적이다.

```kotlin
fun main() {
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
}
```

#### 5.2.3. groupBy: 리스트를 여러 그룹으로 이뤄진 맵으로 변경

* 컬렉션의 모든 원소를 어떤 특성에 따라 여러 그룹으로 나누는 경우에 사용한다.

```kotlin
fun main() {
    val needGroupingPeople = listOf(Person("Alice", 29), Person("Bob", 31), Person("Carol", 31))
    println(needGroupingPeople.groupBy { it.age }) // {29=[Person(name=Alice, age=29)], 31=[Person(name=Bob, age=31), Person(name=Carol, age=31)]}
}
```

#### 5.2.4. flatMap과 flatten: 중첩된 컬렉션 안의 원소 처리

* flatMap 함수
    * 먼저 인자로 주어진 람다를 컬렉션의 모든 객체에 적용한다.
    * 람다를 적용한 결과 얻어지는 여러 리스트를 flatten 함수를 통해 하나의 리스트로 모은다.

```kotlin
fun main() {
    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() }) // [a, b, c, d, e, f]
    println(strings.map { it.toList() }.flatten()) // [a, b, c, d, e, f]

    val books = listOf(
        Book("Thursday Next", listOf("Jasper Fforde")),
        Book("Mort", listOf("Terry Pratchett")),
        Book("Good Omens", listOf("Terry Pratchett", "Neil Gainman"))
    )
    println(books.flatMap { it.authors }.toSet()) // [Jasper Fforde, Terry Pratchett, Neil Gainman]
}
```

### 5.3. 지연 계산(lazy) 컬렉션 연산

* map, filter 같은 몇 가지 컬렉션 함수들은 결과 컬렉션을 즉시(eagerly) 생성한다.
    * 컬렉션 함수를 연쇄하면 매 단계마다 계산 중간 결과를 새로운 컬렉션에 임시로 담는다.
    * 시퀀스(sequence)를 사용하면 중간 임시 컬렉션을 사용하지 않고 컬렉션 연산을 연쇄할 수 있다.
* 중간 결과를 저장하는 컬렉션이 생기지 않기 때문에 원소가 많은 경우 성능이 좋아진다.
    * 코틀린 지연 계산 시퀀스는 Sequence 인터페이스에서 시작한다.
* Sequence 인터페이스의 강점은 필요로 할 때 비로소 계산된다는 것이다.
    * 중간 처리 결과를 저장하지 않고도 연산을 연쇄적으로 적용해서 효율적으로 계산을 수행할 수 있다.
* asSequence 확장 함수를 호출하면 어떤 컬렉션이든 시퀀스로 바꿀 수 있다.
* 큰 컬렉션에 대해서 연산을 연쇄시킬 때는 시퀀스를 사용하는 것이 좋다.

```kotlin
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
            .toList() // sequence to collection
    )
```

#### 5.3.1. 시퀀스 연산 실행: 중간 연산과 최종 연산

* 시퀀스에 대한 연산은 중간(intermediate) 연산과 최종(terminal) 연산으로 나뉜다.
* 중간 연산은 다른 시퀀스를 반환한다.
    * 최초 시퀀스의 원소를 변환하는 방법이 쌓여있는 시퀀스이다.
* 최종 연산은 결과를 반환한다.
    * 결과는 최초 컬렉션에 대해 변환을 적용한 시퀀스로부터 일련의 계산을 수행해 얻을 수 있는 컬렉션이나 원소, 숫자 또는 객체이다.
* 중간 연산은 항상 지연 계산된다.
    * 최종 연산이 없는 경우 코드를 실행해도 아무 동작을 하지 않는다.
    * 최종 연산이 호출될 때 적용된다.

* 중간 연산만 있는 경우
    * 별도로 출력되는 로그가 없다.

```kotlin
fun main() {
    listOf(1, 2, 3, 4, 5).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
}
```

* 최종 연산까지 동작한 경우

```kotlin
fun main() {
    listOf(1, 2, 3, 4, 5).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList()
}
```

* 다음과 같은 진행 로그를 볼 수 있다.

```
map(1) filter(1) map(2) filter(4) map(3) filter(9) map(4) filter(16) map(5) filter(25)
```

* 시퀀스의 경우 모든 연산은 각 원소에 대해 순차적으로 적용된다.
    * 컬렉션을 통째로 처리하고 다음 연산을 처리하는 방법이 아니다.
* map과 find 연산을 통해 이를 확인할 수 있다.
    * map으로 리스트의 각 숫자를 제곱한다.
    * 제곱한 숫자 중에서 find로 5보다 큰 첫 번째 원소를 찾는다.

* 컬렉션 연산

```kotlin
fun main() {
    listOf(1, 2, 3, 4, 5)
        .map { print("map($it) "); it * it }
        .find { print("find($it) "); it > 5 }
}
```

* 컬렉션 모든 원소에 대해 map 연산을 수행한다.
* 그 다음 임시 컬렉션에 담긴 원소들을 대상으로 find 연산을 수행한다.

```
map(1) map(2) map(3) map(4) map(5) find(1) find(4) find(9) 
```

* 시퀀스 연산

```kotlin
fun main() {
    listOf(1, 2, 3, 4, 5).asSequence()
        .map { print("map($it) "); it * it }
        .find { print("find($it) "); it > 5 }
}
```

* 4, 5 원소에 대한 map, find 연산을 수행하지 않는다.

```
map(1) find(1) map(2) find(4) map(3) find(9) 
```

#### 5.3.2. 시퀀스 만들기

* 시퀀스를 만드는 다른 방법은 generateSequence 함수를 사용하는 것이다.
    * sum 메소드가 최종 연산이다.

```kotlin
fun main() {
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum()) // 5050
}
```

### 5.4. 자바 함수형 인터페이스 활용

* 코틀린 개발에서 다뤄야 하는 API 중 상당수는 자바로 작성된 경우일 것이다.
    * 코틀린 람다를 자바 API에 사용해도 아무 문제가 없다.
* 함수형 인터페이스(funtional interface) 또는 SAM(single abstract method) 인터페이스를 사용한다.
    * SAM(single abstract method)은 단일 추상 메소드라는 의미이다.
    * 자바 API에는 Runnable이나 Callable과 같은 함수형 인터페이스와 그런 함수형 인터페이스를 활용하는 메소드가 많다.
    * 코틀린은 함수형 인터페이스를 인자로 취하는 자바 메소드를 호출할 때 람다를 넘길 수 있게 한다.
    * 코틀린 코드는 무명 클래스 인스턴스를 정의하고 활용할 필요가 없다.

#### 5.4.1. 자바 메소드에 람다를 인자로 전달

* 함수형 인터페이스를 인자로 원하는 자바 메소드에 코틀린 람다를 전달할 수 있다.
    * 컴파일러는 자동으로 람다를 Runnable 인스턴스로 변환한다.
    * 컴파일러는 자동으로 무명 클래스와 인스턴스를 만든다.
    * 무명 클래스에 있는 유일한 추상 메소드를 구현할 때 람다 본문을 메소드 본문으로 사용한다.

```kotlin
fun postponeComputation(delay: Int, computation: Runnable) {}

fun main() {
    postponeComputation(1000) { println(42) }
}
```

* 람다와 무명 객체 사이에는 차이가 있다.
* 객체를 명시적으로 선언하는 경우 메소드를 호출할 때마다 새로운 객체가 생성된다.
* 람다는 무명 객체를 호출할 때마다 반복 사용한다.
    * 전역 변수로 컴파일하고 프로그램 안에 단 하나의 인스턴스만 만드는 것과 동일하다.
* 람다가 주변 영역의 변수를 포획한다면 매 호출마다 같은 인스턴스를 사용할 수 없다.
    * 그런 경우 컴파일러는 매번 주변 영역의 변수를 포획한 새오운 인스턴스를 생성한다.

```kotlin
package action.`in`.kotlin.e

fun postponeComputation(delay: Int, computation: Runnable) {}

val runnable = Runnable { println(42) }

fun main() {
    postponeComputation(1000, runnable)
}
```

* 대부분의 경우 람다와 자바 함수형 인터페이스 사이의 변환은 컴파일러에 의해 자동으로 이뤄진다.

#### 5.4.2. SAM 생성자: 람다를 함수형 인터페이스로 명시적으로 변경

* SAM 생성자는 람다를 함수형 인터페이스의 인스턴스로 변환할 수 있게 컴파일러가 자동으로 생성한 함수이다.
* 컴파일러가 자동으로 람다를 함수형 인터페이스 무명 클래스로 바꾸지 못하는 경우 SAM 생성자를 사용할 수 있다.
    * 함수형 인터페이스의 인스턴스를 반환하는 메소드가 있다면 람다를 직접 반환할 수 없다.
    * 반환하고 싶은 람다를 SAM 생성자로 감싸줘야 한다.
* SAM 생성자의 이름은 사용하려는 함수형 인터페이스의 이름과 같다.
    * 함수형 인터페이스의 유일한 추상 메소드의 본문에 사용할 람다만을 인자로 받는다.
    * 함수형 인터페이스를 구현한 클래스의 인스턴스를 반환한다.

```kotlin
fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

fun main() {
    createAllDoneRunnable().run()
}
```

* 람다에는 무명 객체와 달리 인스턴스 자신을 가리키는 this가 없다.
* 람다를 변환한 무명 클래스의 인스턴스를 참조할 방법이 없다.
* 컴파일러 입장에서 보면 람다는 코드 블럭일 뿐 객체가 아니다.
    * 람다 안에서 this는 그 람다를 둘러싼 클래스의 인스턴스를 가리킨다.
* 가끔 오버로드한 메소드 중에서 어떤 타입의 메소드를 선택해 람다를 변환해 넘겨줘야 할지 모호할 때가 있다.
* 명시적으로 SAM 생성자를 적용하면 컴파일 오류를 피할 수 있다.

### 5.5. 수신 객체 지정 람다: with와 apply

* 수신 객체 지정 람다는 수신 객체를 명시하지 않고 람다의 본문 안에서 다른 객체의 메소드를 호출하는 방법이다.

#### 5.5.1. with 함수

* 어떤 객체의 이름을 반복하지 않고도 그 객체에 대해 다양한 연산을 수행하는 방법이다.

* 아래 코드를 with 함수를 사용해 변경할 수 있다.

```kotlin
package action.`in`.kotlin.f

fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

fun main() {
    println(alphabet())
}
```

* 다음과 같이 변경한다.

```kotlin
fun alphabetWith(): String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) { // 메소드를 호출하려는 수신 객체를 지정한다.
        for (letter in 'A'..'Z') {
            this.append(letter) // this를 명시해서 앞에서 지정한 수신 객체의 메소드를 호출한다.
        }
        append("\nNow I know the alphabet!") // this를 생략하고 메소드를 호출한다.
        this.toString() // 람다에서 값을 반환한다.
    }
}

fun main() {
    println(alphabetWith())
}
```

* with 문은 언어가 제공하는 특별한 구문 같지만 실제로는 파라미터가 두 개 있는 함수다.
* 첫 번째 파라미터는 stringBuilder, 두 번째 파라미터는 람다이다.
    * 람다를 괄호 밖으로 빼내는 관례를 사용함에 따라 전체 함수 호출이 언어가 제공하는 특별 구문처럼 보인다.
* with 함수는 첫 번째 인자로 받은 객체를 두 번째 인자로 받은 람다의 수신 객체로 만든다.
    * 인자로 받은 람다 본문에서는 this를 사용해 그 수신 객체에 접근할 수 있다.
    * 일반적인 this와 마찬가지로 this와 점(.)을 사용하지 않고 프로퍼티나 메소드 이름만 사용해도 수신 객체의 멤버에 접근할 수 있다.
* with가 반환하는 값은 람다 코드를 실행한 결과이다.
    * 그 결과는 람다 식의 본문에 있는 마지막 식의 값이다.
    * 람다의 결과 대신 수신 객체가 필요한 경우도 있으며 그런 경우 apply 라이브러리 함수를 사용한다.

#### 5.5.2. apply 함수

* with 함수와 거의 유사하다.
* 유일한 차이는 apply는 항상 자신에게 전달된 객체를 반환한다.
    * apply는 확장 함수로 정의되어 있다.
    * apply의 수신 객체가 전달받은 람다의 수신 객체가 된다.
    * apply를 실행한 결과는 StringBuilder 객체이다.

```kotlin
fun alphabetApply(): String = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

fun main() {
    println(alphabetApply())
}
```

* apply 함수는 객체의 인스턴스를 만들면서 즉시 프로퍼티 중 일부를 초기화해야 하는 경우 유용하다.
* 자바에서는 보통 별도의 Builder 객체가 이런 역할을 담당한다.
* 코틀린에서는 어떤 클래스가 정의돼 있는 라이브러리의 특별한 지원 없이도 그 클래스에 대해 apply를 활용할 수 있다.
* apply 함수를 사용하면 함수의 본문에 간결한 식을 사용할 수 있다.
    * buildString 함수를 사용하면 StringBuilder 객체를 만드는 일과 toString 함수를 호출하는 것을 알아서 해준다.

```kotlin
fun alphabetBuildString(): String = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}

fun main() {
    println(alphabetBuildString())
}
```
