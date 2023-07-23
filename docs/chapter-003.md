
## Chapter 3. 함수 정의와 호출

### 3.1. 코틀린에서 컬렉션 만들기

* 맵을 만들 때 to 키워드는 특별한 키워드가 아니라 일반 함수이다.

```kotlin
package action.`in`.blog.a

val set = hashSetOf(1, 2, 3, 7, 53)
val list = arrayListOf(1, 2, 3, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

fun main(args: Array<String>) {
    println(set)
    println(set.javaClass)

    println(list)
    println(list.javaClass)

    println(map)
    println(map.javaClass)
}
```

* 결과

```
[1, 2, 3, 53, 7]
class java.util.HashSet
[1, 2, 3, 7, 53]
class java.util.ArrayList
{1=one, 53=fifty-three, 7=seven}
class java.util.HashMap
```

* 코틀린은 자신만의 컬렉션 기능을 제공하지 않는다.
* 자바 개발자가 기존 컬렉션을 활용할 수 있다는 의미이다.
* 코틀린이 자체 컬렉션을 제공하지 않는 이유
    * 자바 코드와 상호 작용하기 쉽다.
* 코틀린 컬렉션은 자바 컬렉션과 똑같은 클래스이다.
    * 자바보다 더 많은 기능을 사용할 수 있다.

```kotlin
    println(set.max())
    println(set.min())
    println(set.last())

    println(list.max())
    println(list.min())
    println(list.last())
```

### 3.2. 함수를 호출하기 쉽게 만들기

```kotlin
package action.`in`.blog.b

fun <T> joinToString(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
): String {
    val result = StringBuffer(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun main() {
    
    val list = listOf(1, 2, 3)
    
    println(joinToString(list, ";", "(", ")")) // (1;2;3)
}
```

* 위와 같은 함수 호출은 혼란을 만들 수 있다.
    * 어느 위치가 구분자인지 시작, 종료 문자열인지 확인하기 어렵다.
* 인자 전달을 확실하게 할 수 있도록 다음과 같은 기능을 제공한다.
* 호출 시 인자 중 어느 하나라도 이름을 명시하고 나면 혼동을 막기 위해 그 뒤에 오는 모든 인자는 이름을 명시해야한다.

```kotlin
fun main() {
    
    val list = listOf(1, 2, 3)
    
    println(joinToString(list, ";", "(", ")"))
    
    println(joinToString(list, separator = ";", prefix = "(", postfix = ")"))
}
```

#### 3.2.2. 디폴트 파라미터 값

* 자바는 오버로딩(overloading)한 메소드가 너무 많아진다는 문제점이 있다.
* 코틀린에서는 함수 선언에서 파라미터의 디폴트 값을 지정할 수 있다.
    * 이를 통해 오버로딩을 피할 수 있다.
* 디폴트 파라미터 값은 함수를 선언할 때 지정한다.

```kotlin
package action.`in`.blog.b

fun <T> joinToString(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuffer(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun main() {

    val list = listOf(1, 2, 3)

    println(joinToString(list, ";", "(", ")")) // (1;2;3)

    println(joinToString(list, separator = ";", prefix = "(", postfix = ")")) // (1;2;3)

    println(joinToString(list)) // [1,2,3]
}
``` 

#### 3.2.3. 정적인 유틸리티 클래스 없애기

* 자바는 모든 코드를 클래스의 메소드로 작성해야 한다.
    * 다양한 정적 메소드를 모아두는 역할을 담당하는 클래스가 만들어졌다.
* 코틀린은 이런 무의미한 클래스는 필요 없다.
    * 함수를 직접 소스 파일의 최상위 수준, 모든 다른 클래스의 밖에 위치시키면 된다.
* JVM은 클래스 안에 들어있는 코드만 실행할 수 있다.
* 파일 최상단에 정의한 함수를 사용할 수 있도록 새로운 클래스를 정의해준다.
* 아래와 같은 코드가 join.kt 파일 안에 존재한다고 가정한다.

```kotlin
package strings

fun joinToString(...): String {...}
```

* 컴파일 되면 다음과 같은 모습이 된다.
    * 파일에 대응하는 클래스의 이름을 변경할 때 @JvmName 애너테이션을 사용한다.

```java
package strings;

public class JoinKt {
    public static String joinToString(...) {...}
}
```

* 프로퍼티도 파일의 최상위 수준에 놓을 수 있다.
* 어떤 데이터를 클래스 밖에 위치시켜야 하는 경우는 흔하지 않지만, 종종 유용하다.
* 최상위 프로퍼티의 값은 정적 필드에 저장된다.
* 상수처럼 사용하기 위해 `public static final`로 선언하려면 `const`를 사용한다.

```kotlin
const val UNIX_LINE_SEPARATOR = "\n"

// java
// public static final String UNIX_LINE_SEPARATOR = "\n";
```

### 3.3. 메소드를 다른 클래스에 추가

* 기존 코드와 코틀린 코드를 자연스럽게 통합하는 것이 코틀린의 핵심 목표 중 하나이다.
* 확장 함수를 사용하면 기존 자바 API를 재작성하지 않고도 코틀린이 제공하는 여러 편리한 기능을 사용할 수 있다.
* 확장 함수는 어떤 클래스의 멤버 메소드인 것처럼 호출할 수 있지만, 그 클래스의 밖에 선언된 함수다.
* 아래 예제를 살펴본다.
    * String 클래스에 새로운 메소드를 추가할 수 있다.
    * String 클래스를 직접 작성한 코드가 아니고 심지어 코드를 소유한 것은 아니지만, 원하는 메소드를 String 클래스에 추가할 수 있다.
    * String - 수신 객체 타입
    * this - 수신 객체, 로직 안에서 생략 가능

```kotlin
package action.`in`.blog.c

fun String.lastChar(): Char = this.get(this.length - 1)

fun main() {

    val message = "Hello World"
    
    println(message.lastChar())
}
```

* 확장 함수 내부에서는 일반적인 인스턴스 메세지의 내부에서와 마찬가지로 수신 객체의 메소드나 프로퍼티를 바로 사용할 수 있다.
* 확장 함수가 캡슐화를 깨지는 않는다.
* 클래스 안에서 정의한 메소드와 달리 확장 함수 안에서는 클래스 내부에서만 사용할 수 있는 비공개(private) 멤버나 보호된(protected) 멤버를 사용할 수 없다.

#### 3.3.1. 임포트와 확장 함수

* 확장 함수를 정의했다고 해도 자동으로 프로젝트 안의 모든 소스 코드에서 그 함수를 사용할 수 있지는 않다.
* 확장 함수를 사용하기 위해서는 그 함수를 다른 클래스나 함수와 마찬가지로 임포트해야 한다.
    * `*` 을 사용하면 모든 함수를 임포트할 수 있다.
* as 키워드를 사용하면 메소드 이름을 변경할 수 있다.
    * 한 파일 안에서 다른 여러 패키지에 속해 있는 이름이 같은 함수를 사용하는 경우 이름을 바꿔 임포트하면 충돌을 방지할 수 있다.

```kotlin
package action.`in`.blog.d

import action.`in`.blog.c.lastChar as last

fun main() {
    // println("Hello World".lastChar())
    println("Hello World".last())
}
```

#### 3.3.2. 자바에서 확장 함수 호출 

* 내부적으로 확장 함수는 수신 객체를 첫번째 인자로 받는 정적 메소드이다.
* 확장 함수를 호출해도 다른 어댑터 객체나 실행 시점 부가 비용이 들지 않는다.
* 최상위 함수와 마찬가지로 확장 함수가 들어있는 자바 클래스 이름도 확장 함수가 들어있는 파일 이름에 따라 결정된다.

```java
char c = StringUtilsKt.lastChar("Hello World");
```

#### 3.3.3. 확장 함수로 유틸리티 함수 정의

* `Collection<T>`에 대한 확장 함수를 선언한다.
* 내부에서 사용되는 this는 수신 객체를 가르킨다.
    * 여기선 T 타입의 원소로 이뤄진 컬렉션이다.

```kotlin
package action.`in`.blog.e

fun <T> Collection<T>.customJoinToString(
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuffer(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun main() {
    val list = listOf(1, 2, 3, 5, 6, 7, 100)
    println(list.customJoinToString(";", "(", ")")) // (1;2;3;5;6;7;100)
}
```

* 확장 함수는 정적 메소드 호출에 대한 문법적인 편의(syntatic sugar)일 뿐이다.
* 구체적인 타입을 수신 객체 타입으로 지정할 수 있다.
    * 아래는 문자열 컬렉션에 대한 join 함수를 정의했다.
    * 숫자 컬렉션으로 join 함수를 호출하면 컴파일 에러가 발생한다.

```kotlin
package action.`in`.blog.e

fun <T> Collection<T>.customJoinToString(
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val result = StringBuffer(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun Collection<String>.join(
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
) = customJoinToString(separator, prefix, postfix)

fun main() {
    val list = listOf(1, 2, 3, 5, 6, 7, 100)
    println(list.customJoinToString(";", "(", ")"))

    val stringList = listOf("1", "2", "3")
    println(stringList.join())
}
```

#### 3.3.4. 확장 함수는 오버라이드할 수 없다

* 확장 함수가 정적 메소드와 같은 특징을 가지므로 확장 함수를 하위 클래스에서 오버라이드하지 못한다.
* 확장 함수는 클래스의 일부가 아니다.
    * 확장 함수는 클래스 밖에 선언된다.
    * 이름과 파라미터가 완전히 같은 확장 함수를 기반 클래스와 하위 클래스에 대해 정의해도 실제로는 확장 함수를 호출할 때 수신 객체로 지정한 변수의 정적 타입에 의해 어떤 확장 함수가 호출될지 결정된다.
    * 변수에 저장된 객체의 동적인 타입에 의해 확장 함수가 결정되지 않는다.
* 아래 예제처럼 View 참조 변수가 가르키는 실제 객체는 Button 이지만, 변수 타입이 View 이기 때문에 확장 함수는 View에 정의된 것이 사용된다.
* 코틀린은 호출될 확장 함수를 정적으로 결정하기 때문에 확장 함수를 오버라이드할 수 없다.

```kotlin
package action.`in`.blog.f

open class View {
    open fun click() = println("View Clicked")
}

class Button : View() {
    override fun click() = println("Button Clicked")
}

fun View.shutoff() = println("I am a view")
fun Button.shutoff() = println("I am a button")

fun main() {

    val button = Button()
    button.click() // Button Clicked
    button.shutoff() // I am a button

    val view: View = Button()
    view.click() // Button Clicked
    view.shutoff() // I am a view
}
```

#### 3.3.5. 확장 프로퍼티

* 확장 프로퍼티를 사용하면 기존 클래스 객체에 대한 프로퍼티 형식의 구문으로 사용할 수 있는 AP를 추가할 수 있다.
* 프로퍼티라는 이름으로 불리기는 하지만 상태를 저장할 적절한 방법이 없기 때문에 실제로 확장 프로퍼티는 아무 상태도 가질 수 없다.
* 확장 함수처럼 확장 프로퍼티도 일반적인 프로퍼티와 같은데 단지 수신 객체 클래스가 추가될 뿐이다.
    * 뒷받침하는 필드가 없어서 기본 게터 구현을 제공할 수 없으므로 최소한 게터는 꼭 정의해야 한다.
    * 초기화 코드에서 계산한 값을 담을 장소가 전혀 없으므로 초기화 코드도 쓸 수 없다.

```kotlin
package action.`in`.blog.g

val String.lastChar: Char
    get() = get(length - 1)

fun main() {

    println("This is fake".lastChar)
}
```

* 프로퍼티를 변경 가능하게 만들려면 var 타입으로 선언한다.

```kotlin
package action.`in`.blog.extend

val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }

fun main() {

    println("This is fake".lastChar)

    val builder = StringBuilder("This is banana")
    builder.lastChar = 'A'
    println(builder.toString())
}
```

### 3.4. 컬렉션 처리

* 코틀린은 다음과 같은 특징을 가진다.
    * vararg 키워드를 사용하면 호출 시 인자 개수가 달라질 수 있는 함수를 정의할 수 있다.
    * 중위 함수 호출 구문을 사용하면 인자가 하나뿐인 메소드를 간편하게 호출할 수 있다.
    * 구조 분해 선언(destructuring delclaration)을 사용하면 복합적인 값을 분해해서 여러 변수에 나눠 담을 수 있다.

#### 3.4.1. 자바 컬렉션 API 확장

* 코틀린 표준 라이브러리는 수많은 확장 함수를 포함한다.
* 모두 알 필요는 없으니 필요에 따라 찾아보면 된다.

#### 3.4.2. 가변 인자 함수

* 자바의 가변 길이 인자(varargs)는 뒤에 ...을 붙힌다.
* 코틀린은 파라미터 앞에 vararg 변경자를 붙힌다.

```kotlin
public fun <T> listOf(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()
```

#### 3.4.3. 값의 쌍 다루기

* 맵을 만들 때 사용했던 to 라는 단어는 코틀린 키워드가 아니다.
* 중위 호출(infix call)이라는 특별한 방식의 메소드 호출이다.
* 중위 호출 사이에는 수신 객체와 유일한 메소드 인자 사이에 메소드 일므을 는다.

```kotlin
1.to("one") // to 메소드 일반적인 방식 호출
1 to "one" // to 메소드 중위 호출 방식 호출
```

* to 함수는 다음과 같다.
    * 함수 앞에 infix 라는 키워드를 볼 수 있다.
* Pair 객체를 반환한다.
    * 코틀린 표준 라이브러리 클래스로 이름대로 두 원소로 이뤄진 순서쌍을 표현한다.

```kotlin
public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
```

* 다음과 같이 구조 분해 선언을 사용해 변수에 값을 할당할 수 있다.

```kotlin
package action.`in`.blog.h

fun main() {

    // 구조 분해 선언
    val (number, name) = 1 to "one"

    println(number)
    println(name)
}
```

### 3.5. 문자열과 정규식 다루기

* 코틀린 문자열은 자바 문자열과 동일하다.

#### 3.5.1. 문자열 나누기

* 자바 문자열의 split 함수는 정규식을 받는다.
* 코틀린은 자바의 split 대신 여러 가지 다른 조합의 파라미터를 받는 split 확장 함수를 제공한다.
* 정규식을 파라미터로 받는 함수는 String이 아닌 Regex 타입의 값을 받는다.

```kotlin
package action.`in`.blog.i

fun main() {
    println("12.345-6.A".split(".", "-"))
    println("12.345-6.A".split("[.-]".toRegex()))
    println("12.345-6.A".split("\\.|-".toRegex()))
}
```

#### 3.5.2. 정규식과 3중 따옴표로 묶은 문자열

* 코틀린에서는 정규식을 사용하지 않고도 문자열을 쉽게 파싱할 수 있다.
* 정규식은 강력하지만, 나중에 알아보기 힘든 경우가 많다.

```kotlin
package action.`in`.blog.i

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    println("Dir: $directory, name: $fileName, ext: $extension")
}

fun main() {

    parsePath("/Users/yole/kotlin-book/chapter.adoc")
}
```

* 이를 정규식과 3중 따옴표로 묶은 문자열을 사용해 처리할 수도 있다.
* 3중 따옴표 문자열에서는 역슬래시를 포함한 어떤 문자도 이스케이프할 필요가 없다.
* 매치에 성공하면 그룹별로 분해한 매치 결과를 의미하는 destructured 프로퍼티를 각 변수에 대입하여 사용할 수 있다.

```kotlin
package action.`in`.blog.i

fun parsePath(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, fileName, extension) = matchResult.destructured
        println("Dir: $directory, name: $fileName, ext: $extension")
    }
}

fun main() {

    parsePath("/Users/yole/kotlin-book/chapter.adoc")
}
```

#### 3.5.3. 여러 줄 3중 따옴표 문자열

* 3중 따옴표 문자열을 문자열 이스케이프를 피하기 위해서만 사용하지 않는다.
* 줄 바꿈을 표현하는 아무 문자열이나 그대로 들어간다.
* 프로그래밍시 여러 줄 문자열이 요긴한 분야는 테스트이다.

### 3.6. 코드 다듬기

* 좋은 코드의 중요한 특징 중 하나는 중복이 없어야 한다는 것이다.
    * DRY - Don't Repeat Yourself
* 자바 코드를 작성할 때 DRY 원칙을 피하기는 쉽지 않다.
* 코틀린은 함수에서 추출한 함수를 원함수 내부에 중첩시킬 수 있다.
    * 함수 중첩은 최대 한 단계 정도만 권장한다.
    * 함수의 깊이가 깊어지면 코드를 읽는 것이 상당히 어려워진다.

* 다음과 같은 코드를 내부 함수를 사용해 리팩토링할 수 있다.

```kotlin
package action.`in`.blog.j

class User(
    val id: Int, val name: String, val address: String
)

fun saveUser(user: User) {
    if (user.name.isEmpty()) {
        throw IllegalArgumentException("Can't save user ${user.id}: empty name")
    }
    if (user.address.isEmpty()) {
        throw IllegalArgumentException("Can't save user ${user.id}: empty address")
    }
    println("Save user data")
}

fun main() {
    saveUser(User(1, "", ""))
}
```

* 검증 로직의 중복을 사라지게 만들었다.

```kotlin
package action.`in`.blog.j

class User(
    val id: Int, val name: String, val address: String
)

fun saveUser(user: User) {
    fun validate(user: User, value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")
        }
    }

    validate(user, user.name, "name")
    validate(user, user.address, "address")

    println("Save user data")
}

fun main() {
    saveUser(User(1, "", ""))
}
```

* 로컬 함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있다.
    * user 객체를 파라미터로 넘겨주지 않아도 된다.

```kotlin
package action.`in`.blog.j

class User(
    val id: Int, val name: String, val address: String
)

fun saveUser(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")
        }
    }

    validate(user.name, "name")
    validate(user.address, "address")

    println("Save user data")
}

fun main() {
    saveUser(User(1, "", ""))
}
```

* User 클래스를 확장한 함수로 만들 수 있다.
    * 코드를 확장 함수로 뽑아내는 기법은 유용하다.

```kotlin
package action.`in`.blog.j

class User(
    val id: Int, val name: String, val address: String
)

fun User.validate() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${this.id}: empty $fieldName")
        }
    }
    validate(name, "name")
    validate(address, "address")
}

fun saveUser(user: User) {
    user.validate()
    println("Save user data")
}

fun main() {
    saveUser(User(1, "Hello", "World"))
}
```

* 다음과 같이 객체에 포함시켜도 된다.

```kotlin
package action.`in`.blog.j

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
```