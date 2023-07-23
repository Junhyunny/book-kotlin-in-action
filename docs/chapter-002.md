
## Chapter 2. 코틀린 기초

대부분의 코틀린 제어 구조는 자바와 비슷하지만, 몇 가지 중요한 개선이 이뤄졌다. 
스마트 캐스트(smart cast)는 타입 검사와 타입 캐스트, 타입 강제 변환을 하나로 엮은 기능이다. 

### 2.1. 기본 요소

* 파라미터 이름 뒤에 타입을 작성한다.
* 함수를 최상위 수준에서 정의할 수 있다.
    * 반드시 클래스 안에 함수를 넣을 필요가 없다.
* System.out.println 대신 println 이라고 작성한다.
    * 코틀린 표준 라이브러리는 여러 가지 표준 자바 라이브러리 함수를 간결하게 사용할 수 있게 감싼 래퍼(wrapper)를 제공한다.
    * 줄 마지막에 세미콜론(;)이 필요하지 않다.

#### 2.1.2. 함수

* 반환 타입은 파라미터 목록 뒤에 지정한다.

```kotlin
fun main(args: Array<String>) {
    println("Hello World")
    print(max(1, 2))
}

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}
```

* 문(statement)과 식(expression) 구분
    * 코틀린에서 if는 문이 아니라 식이다.
    * 식은 값을 만들어 내며 다른 식의 하위 요소로 계산에 참여할 수 있다.
    * 문은 자신을 둘러싸고 있는 가장 안쪽 블록의 최상위 요소로 존재하며 아무런 값을 만들어내지 않는다.
    * 코틀린에서 루프를 제외한 대부분의 제어 구조가 식이다.
    * 제어 구조를 다른 식으로 엮어낼 수 있으면 일반적인 패턴을 아주 간결하게 표현할 수 있다.

* 등호(=)를 사용하면 if 식을 더 간결하게 표한할 수 있다.

```kotlin
fun max(a: Int, b: Int): Int = if (a > b) a else b
```

* 타입 추론이 가능하기 때문에 반환 타입을 생략할 수 있다.
* 식이 본문인 함수의 반환 타입만 생략 가능하다.
* 블록이 본문인 함수가 값을 반환한다면 반드시 반환 타입을 지정해야 한다.
    * return 문을 사용해 반환 값을 명시해야한다.

```kotlin
fun max(a: Int, b: Int) = if (a > b) a else b
```

#### 2.1.3. 변수

* 변경 가능한 변수와 변경 불가능한 변수
* val(from value)
    * 변경 불가능한 참조를 저장하는 변수
    * val로 선언된 변수는 일단 초기화되면 재대입이 불가능하다.
    * 자바로 말하면 final
* var(from variable)
    * 변경 가능한 참조를 저장하는 변수
    * 변수의 값은 바뀔 수 있다.
    * 자바의 일반 변수에 해당한다.

* 기본적으로 모든 변수는 val 키워드를 사용해 불변 변수로 선언한다.
* 꼭 필요한 경우에만 var 키워드로 변경한다.
* val 변수는 블록을 실행할 때 정확히 한 번만 초기화되야 한다.
* 어떤 블록이 실행될 때 오직 한 초기화 문장만 실행됨을 컴팡이러가 확인할 수 있다면 조건에 따라 val 값을 여러 값으로 초기화할 수 있다.

#### 2.1.4. 더 쉽게 문자열 형식 지정

* 문자열 템플릿 기능이 존재한다.
* 변수를 선언하고 다음 줄에 있는 문자열 리터럴 안에서 그 변수를 사용한다.
* 문자열 리터럴의 필요한 곳에 변수를 넣되 변수 앞에 $를 추가한다.

```kotlin
fun main(args: Array<String>) {
    val name = if (args.isNotEmpty()) args[0] else "Kotlin"
    println("Hello $name")
}
```

* 컴파일러는 정적 검사를 수행하므로 존재하지 않는 변수를 템플릿 안에서 사용하면 컴파일 오류가 발생한다.
* 복잡한 식은 중괄호({})로 둘러싸서 문자열 템플릿에 추가할 수 있다.

```kotlin
fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        println("Hello ${args[0]}")
    }
}
```

* 문자열 템플릿에 중괄호를 사용하면 식을 넣을 수 있다.

```kotlin
println("Hello ${if (args.isNotEmpty()) args[0] else "Kotlin"}")
```

### 2.2. 클래스와 프로퍼티

* 아래는 단순한 값 객체를 선언하는 방법이다.
* 코틀린의 기본 가시성은 public 이다.

```kotlin
package action.`in`.blog.b

class Person(val name: String)
```

#### 2.2.1. 프로퍼티

* 클래스라는 개념의 목적은 데이터를 캡슐화(encapsulate)하고 캡슐화한 데이터를 다루는 코드를 한 주체 아래 가두는 것이다.
* 자바에서 데이터를 필드(field)에 저장하고, 멤버 필드의 가시성은 보통 비공개(private)이다.
* 다음과 같은 방법으로 변경 가능한 프로퍼티를 선언한다.

```kotlin
class Person(
    val name: String, // 읽기 전용 프로퍼티, 코틀린은 비공개 필드와 필드를 읽는 단순한 공개 게터(getter)를 만든다.
    var isMarried: Boolean // 쓸 수 있는 프로퍼티로 비공개 필드와 공개 게터, 공개 세터(setter)를 만든다.
)
```

#### 2.2.2. 커스텀 접근자

* 프로퍼티의 접근자를 직접 작성하는 방법이 있다. 
* isSquare 프로퍼티에는 자체 값을 저장하는 필드가 필요 없다.
    * 자체 구현을 제공하는 게터만 존재한다.
* 클라이언트가 프로퍼티에 접근할 때마다 게터가 프로퍼티 값을 매번 다시 계산한다. 

```kotlin
package action.`in`.blog.c

import java.util.*

class Rectangle(
    val height: Int,
    val width: Int
) {
    val isSquare: Boolean
        get() = height == width
}

fun createRandomRectangle(): Rectangle {
    val random = Random()
    return Rectangle(random.nextInt(), random.nextInt())
}

fun main(args: Array<String>) {

    val rectangle = Rectangle(41, 43)
    println(rectangle.isSquare)
    println(createRandomRectangle().isSquare)
}
```

#### 2.2.3. 코틀린 소스 코드 구조

* 자바의 경우 모든 클래스를 패키지 단위로 관리한다.
* 모든 코틀린 파일의 맨 앞에 package 문을 넣을 수 있다.
* 같은 패키지에 속해 있다면 다른 파일에서 정의한 선언일지라도 직접 사용할 수 있다.
* 다른 패키지에 정의한 선언을 사용하려면 임포트(import)를 통해 선언을 불러온다.

### 2.3. 선택 표현과 처리

#### 2.3.1. enum 클래스 정의

* enum은 자바 선언보다 코틀린 선언에 더 많은 키워드를 쓴다.
* 코틀린에서 enum은 소프트 키워드(soft keyword)라 부르는 존재다.
* enum은 클래스 앞에 있을 때 특별한 의미를 지닌다.
    * 다른 곳에서는 이름에 사용할 수 있다.
* 자바와 마찬가지로 enum은 단순히 값만 열거하는 존재가 아니다.
* enum 클래스 안에도 프로퍼티나 메소드를 정의할 수 있다.

```kotlin
package action.`in`.blog.d

enum class Color(
    val red: Int, val green: Int, val blue: Int // 상수의 프로퍼티를 정의한다
) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238); // 세미콜론 필수

    fun rgb() = (red * 256 + green) * 256 + blue // enum 클래스 안에서 메소드를 정의한다
}
```

#### 2.3.2. when으로 enum 클래스 다루기

* if 와 마찬가지로 when 도 값을 만들어내는 식이다.
* 식이 본문인 함수에 when 을 바로 사용할 수 있다.
    * 앞의 코드는 color로 전달된 값과 같은 분기를 찾는다.
    * 자바와 달리 각 분기의 끝에 break를 넣지 않아도 된다.
    * 성공적으로 매치된 분기를 찾으면 switch는 그 분기를 실행한다.
    * 한 분기 안에 여러 값을 매치 패턴으로 사용할 수 있으며 그럴 경우 값 사이를 콤마(,)로 분리한다.

```kotlin
fun getMnemonic(color: Color) =
    when (color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }

fun getWarmth(color: Color) =
    when (color) {
        Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
        Color.GREEN -> "neutral"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
//        else -> "other"
    }
```

#### 2.3.3. when과 임의의 객체를 함께 사용

* 코틀린의 when은 자바의 switch보다 훨씬 강력하다.
* 분기 조건에 상수만을 사용할 수 있는 자바 switch와 달리 코틀린의 when 분기 조건은 임의의 객체를 허용한다.
* when 분기 조건 부분에 식을 넣을 수 있기 때문에 많은 경우 코드를 간결하고 아름답게 작성할 수 있다.
* 모든 분기 식에서 만족하는 조건을 찾을 수 없다면 else 분기 문장을 계산한다.

```kotlin
fun mix(color1: Color, color2: Color) =
    when(setOf(color1, color2)) {
        setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
        setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
        setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
        else -> throw RuntimeException("Dirty Color")
    }
```

#### 2.3.4. 인자가 없는 when 사용

* 위의 방식은 when 분기 조건을 확인하기 위해 여러 set 인스턴스를 생성한다.
    * 아래 방식은 추가 객체를 만들지 않는다는 장점은 있지만, 가독성이 더 떨어진다.
* 함수가 너무 자주 호출된다면 불필요한 가비지 객체가 늘어나는 것을 방지하기 위해 함수를 고칠 필요가 있다.
* 코드는 약간 읽기 어려워지지만 성능을 더 향상시키긱 위해 그 정도 비용을 감수해야 하는 경우가 자주 있다.
* when에 아무 인자가 없으려면 각 분기의 조건이 불리언 결과를 계산하는 식이어야 한다.

```kotlin
fun mixOptimized(color1: Color, color2: Color) =
    when {
        (color1 === Color.RED && color2 === Color.YELLOW) || (color1 == Color.YELLOW && color2 == Color.RED) -> Color.ORANGE
        (color1 === Color.YELLOW && color2 === Color.BLUE) || (color1 == Color.BLUE && color2 == Color.YELLOW) -> Color.GREEN
        (color1 === Color.BLUE && color2 === Color.VIOLET) || (color1 == Color.VIOLET && color2 == Color.BLUE) -> Color.INDIGO
        else -> throw RuntimeException("Dirty Color")
    }
```

#### 2.3.5. 스마트 캐스트: 타입 검사와 타입 캐스트를 조합

* 코틀린에서는 is를 사용해 변수 타입을 검사한다.
* is 검사는 instanceof와 비슷하다.
* 자바에서는 어떤 변수의 타입을 instanceof로 확인한 다음에 그 타입에 속한 멤버에 접근하기 위해 명시적으로 변수 타입을 캐스팅한다.
* is 키워드로 검사하고 나면 굳이 변수를 원하는 타입으로 캐스팅하지 않아도 마치 처음부터 그 변수가 원하는 타입으로 선언된 것처럼 사용할 수 있다.
    * 코틀린에서는 프로그래머 대신 컴파일러가 캐스팅을 수행한다.
    * 이를 스마트 캐스트(smart cast)라고 부른다.
* 스마트 캐스트는 그 프로퍼티가 반드시 val 키워드로 선언되어야 하고 커스텀 접근자를 사용한 것도 안된다.
* 원하는 타입으로 명시적으로 타입 캐스팅을 하려면 as 키워드를 사용해야 한다.

```kotlin
package action.`in`.blog.e

import java.lang.IllegalArgumentException

interface Expression
class Num(val value: Int) : Expression // value라는 프로퍼티만 존재하는 단순한 클래스, Expression을 구현
class Sum(val left: Expression, val right: Expression) : Expression // Expression 타입의 객체라면 어떤 것이나 Sum 연산의 인자가 될 수 있다.

fun eval(expression: Expression): Int {
    if (expression is Num) {
        val n = expression as Num // 불필요한 중복 형변환
        return n.value
    }
    if (expression is Sum) {
        return eval(expression.left) + eval(expression.right)
    }
    throw IllegalArgumentException("unknown expression")
}
```

* 코틀린스럽게 코드를 변경하면 다음처럼 작성할 수 있다.

```kotlin
fun evalAsKotlinWithIf(expression: Expression): Int =
    if (expression is Num) expression.value
    else if (expression is Sum) evalAsKotlinWithIf(expression.left) + evalAsKotlinWithIf(expression.right)
    else throw IllegalArgumentException("unknown expression")

fun evalAsKotlinWithWhen(expression: Expression): Int =
    when (expression) {
        is Num -> expression.value
        is Sum -> evalAsKotlinWithIf(expression.left) + evalAsKotlinWithIf(expression.right)
        else -> throw IllegalArgumentException("unknown expression")
    }
```

#### 2.3.7. if와 when의 분기에서 블록 사용

* if나 when 모두 분기에 블록을 사용할 수 있다.
* 블록의 마지막 문장이 블록 전체의 결과가 된다. 
    * 블록 맨 마지막에 그 분기의 결과 값을 위치시킨다.

```kotlin
fun evalWithLogging(expression: Expression): Int =
    when (expression) {
        is Num -> {
            println("num: ${expression.value}")
            expression.value
        }

        is Sum -> {
            val leftValue = evalWithLogging(expression.left)
            val rightValue = evalWithLogging(expression.right)
            println("sum: ${leftValue} + ${rightValue}")
            leftValue + rightValue
        }

        else -> throw IllegalArgumentException("unknown expression")
    }
```

### 2.4. 대상을 이터레이션

* while 루프는 동일하다.
* for 루프는 for-each 루프만 지원한다.

#### 2.4.1. while 루프

* while, do-while 루프가 존재한다.
* 두 루프의 문법은 자바와 동일하다.

#### 2.4.2. 수에 대한 이터레이션

* 코틀린은 범위(range)를 사용한다.
* 범위는 기본적으로 두 값으로 이뤄진 구간이다.
* 코틀린의 범위는 폐구간으로 양 끝을 포함한다.
* 아래 예시 코드는 1에서 10까지 포함한다.

```kotlin
val oneToTen = 1..10
```

* 범위 기능을 사용하면 반복문 구성이 가능하다.
* 증가 값에 대한 제어는 step 키워드를 사용한다.
* 반만 닫힌 구간(half-closed range)에 대한 반복문은 until 키워드를 사용한다.

```kotlin
package action.`in`.blog.f

fun fizzBuzz(number: Int) =
    when {
        number % 15 == 0 -> "FizzBuzz"
        number % 5 == 0 -> "Buzz"
        number % 3 == 0 -> "Fizz"
        else -> "${number}"
    }

fun main(args: Array<String>) {

    for (index in 1..100) {
        println(fizzBuzz(index))
    }

    for (index in 100 downTo 1 step 2) {
        println(fizzBuzz(index))
    }

    for (index in 0 until 100) {
        println(fizzBuzz(index))
    }
}
```

#### 2.4.3. 맵에 대한 이터레이션

* 맵 객체의 in 키워드를 사용한 반복문은 엔트리 형태로 데이터를 추출할 수 있다.

```kotlin
package action.`in`.blog.g

import java.util.*

val binaryTree = TreeMap<Char, String>()

fun main(args: Array<String>) {

    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.code)
        binaryTree[c] = binary
    }

    for ((letter, binary) in binaryTree) {
        println("$letter = $binary")
    }

    for ((key) in binaryTree) {
        println("$key = ${binaryTree[key]}")
    }

    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("${index}: $element")
    }
}
```

#### 2.4.4. in으로 컬렉션이나 범위의 원소 검사

* in 연산자를 사용해 어떤 값이 범위에 속하는지 검사할 수 있다.
* 반대로 !in을 사용하면 어떤 값이 범위에 속하지 않는지 검사할 수 있다.
* in, !in 연산자를 when 식에 사용해도 된다.

```kotlin
package action.`in`.blog.h

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'
fun recognize(c: Char) =
    when (c) {
        in '0'..'9' -> "It is a digit"
        in 'a'..'z', in 'A'..'Z' -> "It is a letter"
        else -> "I don't know"
    }

fun main(args: Array<String>) {

    println(isLetter('A')) // true
    println(isLetter('f')) // true
    println(isNotDigit('v')) // true
    println(isNotDigit('0')) // false

    println(recognize('A'))
    println(recognize('f'))
    println(recognize('v'))
    println(recognize('0'))
}
```

* 범위는 문자에만 국한되지 않는다.
* 비교가 가능한 클래스(Comparable 인터페이스 구현 클래스)라면 인스턴스 객체를 사용해 범위를 만들 수 있다.
* Comparable을 사용하는 범위의 경우 그 범위 내의 모든 객체를 사용해 범위를 만들 수 있다.

```kotlin
    println("Kotlin" in "Java".."Scala") // true
    println("Kotlin" in setOf("Java", "Scala")) // flase
```

### 2.5. 코틀린의 예외 처리

* 자바의 예외 처리와 비슷하다.
* 함수는 정상적으로 종료할 수 있지만, 오류가 발생하면 예외를 던질 수 있다.
* 함수를 호출하는 쪽에서는 그 예외를 잡아 처리할 수 있다.
* 발생한 예외를 함수 호출 단에서 처리(catch)하지 않으면 함수 호출 스택을 거슬러 올라가면서 예외를 처리하는 부분이 나올 때까지 예외를 던진다.
* 자바와 마찬가지로 예외를 처리하려면 try, catch, finally 절을 함께 사용한다.
* 자바와 달리 throws 절이 코드에 없다.
    * 예를 들어 IOException 같은 경우 확인해야하는 예외(checked exception)이므로 예외 처리를 명시적으로 해줘야했다.
    * 코틀린은 확인이 필요한 예외와 불필요한 예외를 구별하지 않는다.
* 코틀린에서는 함수가 던지는 예외를 지정하지 않고 발생한 예외를 잡아내도 되고 잡아내지 않아도 된다.

```kotlin
package action.`in`.blog.i

fun getPercentage(number: Int) =
    if (number in 0..100) number
    else throw IllegalArgumentException("A percentage value must be between 0 and 100: $number")

fun main(args: Array<String>) {
    try {
        val percentage = getPercentage(111)
        println("$percentage")
    } catch (exception: RuntimeException) {
        println("exception message: ${exception.message}")
    } finally {
        println("finally")
    }
}
```

* 자바의 예외 처리는 강력하지만, 프로그래머들이 의미 없이 예외를 다시 던지거나 처리하지 않고 무시하는 경우가 많다.
    * 예외 처리 규칙이 실제로는 오류 발생을 방지하지 못하는 경우가 자주 있다.
* 코틀린은 try-with-resource 구문을 지원하지 않지만, 라이브러리 함수로 같은 기능을 구현할 수 있다.

#### 2.5.2. try를 식으로 사용

* 코틀린의 try 키워드는 if나 when과 마찬가지로 식으로 사용할 수 있다.
* try의 값을 변수에 대입할 수 있다.
* if와 달리 try의 본문을 반드시 중괄호({})로 둘러싸야 한다.
* 다른 문장과 마찬가지로 try의 본문도 내부에 여러 문장이 있으면 마지막 식의 값이 전체 결과 값이다.
* try-catch 문을 식으로 사용할 때 catch 블럭에 return 이 있는 경우
    * catch 블럭 이후 로직을 실행하지 않는다.
    * 함수를 그대로 종료한다.
* try-catch 문을 식으로 사용할 때 catch 블럭에 return 이 없는 경우
    * 코드는 그대로 실행된다.
    * catch 블럭에서 특정 값을 반환하지 않는 경우 Unit 객체가 반환된다.
    * catch 블럭에서 특정 값을 반환하는 경우 해당 값이 반환된다.

```kotlin
fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (exception: NumberFormatException) {
        println("exception message: ${exception.message}")
        0
        // return
    }
    println(number)
}
```