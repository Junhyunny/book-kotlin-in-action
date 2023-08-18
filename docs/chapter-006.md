
## Chapter 6. 코틀린 타입 시스템

### 6.1. 널 가능성(Nullablility)

* 널 가능성(nullability)는 NPE(NullPointException) 오류를 피할 수 있게 돕기 위한 코틀린의 타입 시스템 특성이다.
* 널이 될 수 있는지 여부를 타입 시스템에 추가함으로써 컴파일러가 여러 가지 오류를 컴파일 시 미리 감지한다.
    * 실행 시점에 발생할 수 있는 예외의 가능성을 줄인다.

#### 6.1.1. 널이 될 수 있는 타입

* 널이 될 수 있는 타입을 명시적으로 지원한다.
* 어떤 변수가 널이 될 수 있다면 그 변수에 대해 메소드를 호출하면 NPE가 발생할 수 있으므로 안전하지 않다.
* 코틀린에서 일반적인 함수 선언은 널이 들어갈 수 없다.

```kotlin
package action.`in`.blog.a

fun strLen(s: String) = s.length

fun main() {
    strLen(null) // Null can not be a value of a non-null type String
}
```

* strLen 함수에서 파라미터 s의 타입은 String인데 코틀린에서 이는 항상 String의 인스턴스여야 한다는 의미이다. 
    * 컴파일러는 널이 될 수 있는 값을 strLen에게 인자로 넘기지 못하게 막는다.
    * 이 함수가 널과 문자열을 인자로 받을 수 있게 하려면 타입 이름 뒤에 물음표(?)를 명시해야 한다.
* String?, Int? 등 어떤 타입이든 타입 이름 뒤에 물음표를 붙이면 그 타입의 변수나 프로퍼티에 null 참조를 저장할 수 있다는 뜻이다.

```kotlin
fun strLen(s: String?) = s?.length
```

* 널이 될 수 있는 타입의 변수가 있다면 그에 대해 수행할 수 있는 연산이 제한된다.

```kotlin
fun strLen(s: String?) = s.length // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?
```

* 널이 될 수 있는 값을 널이 될 수 없는 타입의 변수에 대입할 수 없다.

```kotlin
    val x: String? = null
    val y: String = x // Type mismatch. Required: String Found: String?
```

* 널이 될 수 있는 타입의 값을 널이 될 수 없는 타입의 파라미터를 받는 함수에 전달할 수 없다.

```kotlin
package action.`in`.blog.a

fun strLen(s: String) = s.length

fun main() {
    strLen(x) // Type mismatch. Required: String Found: String?
}
```

* 컴파일러에 의한 이런 강제적인 제약 조건은 반드시 null 검사를 수행할 수밖에 없도록 만든다.
* 일단 null과 비교하고 나면 컴파일러는 null이 확실히 아닌 영역에서 null이 아닌 타입의 값처럼 사용한다.

```kotlin

package action.`in`.blog.a

// fun strLen(s: String?) = if (s != null) s.length else 0
fun strLen(s: String?) = s?.length ?: 0

fun main() {

    println(strLen(null))
}
```

#### 6.1.2. 타입의 의미

