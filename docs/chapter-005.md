
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

```
    run { println(42) }
```

* 실행 시점에 코틀린 람다 호출에는 아무런 부가 비용이 들지 않는다.
* 프로그램의 기본 구성 요소와 비슷한 성능을 낸다.
