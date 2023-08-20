
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

#### 6.1.3. 안전한 호출 연산자: ?.

* 코틀린은 안전한 호출 연산자인 ?. 를 제공한다.
* ?. 연산자를 통해 null 검사와 메소드 호출을 한 번의 연산으로 수행한다.

```kotlin
// 아래 두 코드는 동일하다.
s?.toUpperCase()
if (s != null) s.toUpperCase() else null 
```

* s?.toUpperCase() 식의 결과도 null 값이 될 수 있다는 점에서 String?이 된다.

```kotlin
package action.`in`.blog.b

class Employee(
    val name: String,
    val manager: Employee?
)

fun managerName(employee: Employee): String? = employee.manager?.name

fun main() {

    val ceo = Employee("Da Boss", null)
    val developer = Employee("Bob Smith", ceo)
    
    println(managerName(ceo))
    println(managerName(developer))
}
```

* 안전한 호출은 연쇄적으로 사용할 수 있다.

```kotlin

class Address(
    val streetAddress: String,
    val zipCode: Int,
    val city: String,
    val country: String
)

class Company(
    val name: String,
    val address: Address?
)

class Person(
    val name: String,
    val company: Company?
)

fun Person.countryName(): String {
    val country = this.company?.address?.country
    return if (country != null) country else "Unknown"
}

fun main() {

    val person = Person("Dmitry", null)
    println(person.countryName()) // Unknown
}
```

#### 6.1.4. 엘비스 연산자: ?:

* 코틀린은 null 대신 사용할 디폴트 값을 지정할 때 편리하게 사용할 수 있는 연산자를 제공한다.
* 그 연산자를 엘비스(elvis) 연산자라고 한다. (혹은 null coalescing)

```kotlin
package action.`in`.blog.c

fun foo(s: String?) {
    val value: String = s ?: "defualt value"
}
```

* 코틀린에서는 return 이나 throw 등의 연산도 식이다.
* 엘비스 연산자의 우항에 return, throw 등의 연산을 넣을 수 있고, 엘비스 연산자를 더욱 편하게 사용할 수 있다.