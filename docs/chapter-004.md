
## Chapter 4. 클래스, 객체, 인터페이스

* 코틀린의 클래스와 인터페이스는 자바와 약간 다르다.
* 자바와 달리 코틀린 선언은 기본적으로 final이며 public이다.
* 중첩 클래스는 기본적으로 내부 클래스가 아니다.
    * 코틀린의 중첩 클래스에는 외부 클래스에 대한 참조가 없다.
* 코틀린 컴파일러는 번잡스러움을 피하기 위해 유용한 메소드를 자동으로 만들어준다.
* 클래스를 data로 선언하면 컴파일러가 일부 표준 메소드를 생성해준다.
* 코틀린 언어가 제공하는 위임(delegation)을 사용하면 위임을 처리하기 위한 준비 메소드를 직접 작성할 필요가 없다.

### 4.1. 클래스 계층 정의

* 코틀린 가시성/접근 변경자는 자바와 비슷하지만 아무것도 지정하지 않은 경우 기본 가시성은 다르다.
* 코틀린에 새로 도입한 sealed 변경자에 대해 알아본다.

#### 4.1.1. 코틀린 인터페이스

* 코틀린 인터페이스는 자바 8 인터페이스와 비슷하다.
* 추상 메소드뿐 아니라 구현이 있는 메소드도 정의할 수 있다.
    * 자바의 디폴트 메소드와 비슷하다.
* 인터페이스에는 아무런 상태(필드)도 들어갈 수 없다.

```kotlin
package blog.`in`.action.a

interface Clickable {

    fun click()
}
```

* 자바에서는 extends, implements 키워드를 사용하지만, 코틀린에서는 클래스 이름 뒤에 콜론(:)을 붙이고 인터페이스와 클래스 이름을 적는 것으로 클래스 확장과 인터페이스 구현을 모두 처리한다.
* 클래스는 인터페이스를 원하는 만큼 개수 제한 없이 마음대로 구현할 수 있다.
* 클래스는 오로지 하나만 확장할 수 있다.
* 자바와 달리 코틀린에서 override 변경자는 반드시 사용해야 한다.
    * 실수로 상위 클래스의 메소드를 오버라이드하는 것을 방지해준다.
    * 상위 클래스에 있는 메소드와 시그니처가 같은 메소드를 우연히 하위 클래스에서 선언하는 경우 컴파일이 되지 않기 때문에 overrid를 붙이거나 메소드 이름을 바꿔야만 한다.

```kotlin
package blog.`in`.action.a

class Button : Clickable {

    override fun click() {
        println("I was clicked")
    }
}

fun main() {
    Button().click()
}
```

* 인터페이스 메소드도 디폴트 구현을 제공할 수 있다.
    * default를 붙여야 하는 자바 8과 달리 코틀린에서는 메소드를 특별한 키워드로 꾸미지 않는다.
    * 단순히 메소드 본문을 시그니처 뒤에 추가하면 된다.
* click 메소드는 반드시 재정의가 필요하지만, showOff 메소드는 재정의를 생략해서 디폴트 구현을 사용해도 무관하다.

```kotlin
package blog.`in`.action.a

interface Clickable {

    fun click()
    fun showOff() = println("I am Clickable show off") // default method
}
```

```kotlin
package blog.`in`.action.a

interface Focusable {
    
    fun setFocus(boolean: Boolean) {
        println("I ${if (boolean) "got" else "lost"} focus.")
    }

    fun showOff() = println("I am Focusable show off")
}
```

* 동시에 두 개 이상의 인터페이스를 구현할 떄 인터페이스에 동일한 시그니처 메소드가 존재하면 컴파일 에러가 발생한다.
* 클래스에서 해당 메소드를 재정의하면 문제는 해결된다.
* 인터페이스의 디폴트 메소드를 호출하는 것도 가능하다.

```kotlin
package blog.`in`.action.a

class Button : Clickable, Focusable {

    override fun click() {
        println("I was clicked")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
        println("I am show off in Button")
    }
}

fun main() {
    val button = Button()
    button.click()
    button.showOff()
    button.setFocus(false)
}
```

#### 4.1.2. open, final, abstract 변경자

* 자바는 final 키워드가 명시적으로 붙지 않은 경우 모두 상속이 가능하다. 편리하기도 하지만, 문제가 생기는 경우도 많다.
* 취약한 기반 클래스(fragile base class)라는 문제는 하위 클래스가 기반 클래스에 대해 가졌던 가정이 기반 클래스를 변경함으로써 깨져버린 경우에 생긴다.
* 어떤 클래스가 자신을 상속하는 방법에 대해 정확한 규칙을 제공하지 않는다면 그 클래스의 클라이언트는 기반 클래스를 작성한 사람의 의도와 다른 방식으로 메소드를 오버라이드할 위험이 있다.
* 모든 하위 클래스를 분석하는 것은 불가능하다.
* 기반 클래스를 변경하는 경우 하위 크래스의 동작이 예기치 않게 바뀔 수도 있다는 면에서 기반 클래스는 취약하다.
* 특별히 하위 클래스에서 오버라이드하게 의도된 클래스와 메소드가 아니라면 모두 final로 만드는 것이 안정적이다.

```kotlin
package blog.`in`.action.b

import blog.`in`.action.a.Clickable

open class RichButton : Clickable { // open 키워드로 클래스 상속을 허용한다.

    fun disable() {
        // 파이널 함수
        // 하위 클래스가 이 메소드를 오버라이드할 수 없다.
    }

    open fun animate() {
        // 열린 함수
        // 하위 클래스에서 이 메소드를 오버라이드 할 수 있다.
    }

    override fun click() {
        // 상위 클래스에서 선언된 열린 메소드를 오버라이드 한다.
        // 오버라이드한 메소드는 기본적으로 열려있다.
    }
}
```

* 기반 클래스나 인터페이스의 멤버를 오버라이드하는 경우 그 메소드는 기본적으로 열려있다. 
* 오버라이드하는 메소드의 구현을 하위 클래스에서 오버라이드하지 못하게 금지하려면 메소드 앞에 final 키워드를 명시한다.
    * override 키워드가 붙은 메소드는 기본적으로 열려있다.
    * final을 붙혀 하위 클래스가 이를 확장하지 못 하도록 막는다.

```kotlin
open class RichButton: Clickable {
    final override fun click() {}
}
```

* 열린 클래스와 스마트 캐스트
    * 클래스는 기본적으로 상속 가능 상태를 final로 함으로써 얻을 수 있는 큰 이익은 다양한 경우에 스마트 캐스트가 가능하다는 점이다.
    * 스마트 캐스트는 타입 검사 뒤에 변경될 수 없는 변수에만 적용 가능하다.
    * 클래스 프로퍼티의 경우 val 이면서 커스텀 접근자가 없는 경우에만 스마트 캐스트를 쓸 수 있다는 의미이다.
    * 이는 프로퍼티가 final 이어야 한다는 의미이기도 하다.
    * 프로퍼티가 final이 아니라면 그 프로퍼티를 다른 클래스가 상속하면서 커스텀 접근자를 정의함으로써 스마트 캐스트의 요구 사항을 깰 수 있다.

* 자바처럼 클래스를 abstract로 선언할 수 있다.
    * 선언한 추상 클래스는 인스턴스화 할 수 없다.
    * 추상 클래스에는 구현이 없는 추상 멤버가 있기 때문에 하위 클래스에서 그 추상 멤버를 오버라이드해야 한다.
* 추상 멤버는 항상 열려있다.
* 추상 멤버 앞에 open 변경자를 명시할 필요가 없다.

```kotlin
package blog.`in`.action.b

abstract class Animated { // 이 클래스는 추상 클래스이다. 인스턴스를 만들 수 없다.

    abstract fun animate() // 추상 함수이다. 하위 클래스에서 이 함수를 반드시 오버라이드해야 한다.

    open fun stopAnimate() {
        // 추상 클래스에 속헀더라도 비추상함수는 기본적으로 final
        // 원한다면 open으로 오버라이드를 허용할 수 있다.
    }

    fun animateTwice() {
        // 추상 클래스에 속헀더라도 비추상함수는 기본적으로 final
    }
}
```

* 표로 표현하면 다음과 같다.

|변경자|멤버|설명|
|:-|:-|:-|
|fianl|오버라이드 할 수 없음|클래스 멤버 기본 변경자|
|open|오버라이드 할 수 있음|반드시 open을 명시해야 오버라이드 가능|
|abstract|반드시 오버라이드해야 함|추상 클래스의 멤버에만 이 변경자를 붙힐 수 있다. 추상 멤버에는 구현이 있으면 안된다.|
|override|상위 클래스나 인스턴의 멤버를 오버라이드 하는 중|오버라이드하는 멤버는 기본적으로 열려있다. 하위 클래스의 오버라이드를 방지하려면 final 키워드를 명시하면 된다.|

#### 4.1.3. 가시성 변경자(visibility modifier)

* 코드 기반에 있는 선언에 대한 클래스 외부 접근을 제어한다.
    * 어떤 클래스의 구현에 대한 접근을 제한한다.
    * 클래스에 의존하는 외부 코드를 깨지 않고도 클래스 내부 구현을 변경할 수 있다.
* 기본적으로 코틀린의 가시성 변경자는 자바와 비슷하다.
    * public, protected, private
* 아무 변경자도 없는 경우 선언은 모두 공개(public)이다.
* 자바의 기본 가시성인 패키지 전용(package-private)은 코틀린에 없다.
* 코틀린을 패키지를 네임스페이스(namespace)를 관리하기 위한 용도로만 사용한다.
    * 패키지를 가시성 제어에 사용하지 않는다.
* 코틀린은 패키지 전용 가시성에 대한 대안으로 internal이라는 변경자를 사용한다.
    * 모듈 내부에서만 볼 수 있다.
* 모듈(module)은 한 번에 한꺼먼에 컴파일되는 코틀린 파일들을 의미한다.
    * 인텔리J, 이클립스, 메이븐, 크레이들 등의 프로젝트가 모듈이 될 수 있다.
    * 앤트 태스크(task)가 한 번 실행될 때 함께 컴파일되는 파일의 집합도 모듈이 될 수 있다.
* 모듈 내부 가시성은 모듈 구현에 대한 진정한 캡슐화를 제공한다는 장점이 있다.
    * 자바에서는 패키지가 같은 클래스를 선언하기만 하면 어떤 프로젝트의 외부에 있는 코드라도 패키지 내부에 있는 패키지 전용 선언에 쉽게 접근 가능하다.
    * 모듈의 캡슐화가 쉽게 깨진다.
* 코틀린에서는 최상위 선언에 대해 private 가시성을 허용한다.
* 최상위 선언에는 클래스, 함수, 프로퍼티 등이 포함된다.
* 비공개 가시성인 최상위 선언은 그 선언이 들어있는 파일 내부에서만 사용할 수 있다.
    * 최상위 선언된 함수, 프로퍼티, 클래스는 해당 파일에서만 접근 가능하다.
    * 하위 시스템의 자세한 구현 사항을 외부에 감추고 싶을 때 유용한 방법이다.

* 코틀린의 가시성 변경자

|변경자|클래스 멤버|최상위 선언|
|:-|:-|:-|
|public(기본 가시성)|모든 곳에서 볼 수 있다.|모든 곳에서 볼 수 있다.|
|internal|같은 모듈 안에서만 볼 수 있다.|같은 모듈 안에서만 볼 수 있다.|
|protected|하위 클래스 안에서만 볼 수 있다.|최상위 선언에 적용할 수 없다.|
|private|같은 클래스 안에서만 볼 수 있다.|같은 파일 안에서만 볼 수 있다.|

* 다음 예제는 가시성 규칙 위반 사례들이다.
    * 컴파일 시점에 오류가 감지된다.

```kotlin
package blog.`in`.action.c

import blog.`in`.action.a.Focusable

internal open class TalkativeButton: Focusable {
    private fun yell() = println("Hey")
    protected fun whisper() = println("Let's talk")
}

fun TalkativeButton.giveSpeech() { // public 멤버가 자신의 internal 수신 타입인 TalkativeButton 을 노출한다.
    yell() // public 확장 함수는 private yell 함수에 접근할 수 없다.
    whisper() // public 확장 함수는 protected whisper 함수에 접근할 수 없다.
}
```

* 코틀린은 public 확장 함수에서 그보다 가시성이 낮은 internal 타입인 TalkativeButton을 참조하지 못하게 한다.
* 가시성이 더 낮은 경우 참조하지 못한다.
    * 일반적으로 높거나 같은 경우에만 참조가 가능하다.
* 자바는 protected인 경우 같은 패키지에서 접근 가능했지만, 코틀린은 그렇지 않다.
* 클래스를 확장한 함수는 그 클래스의 private, protected 멤버에 접근할 수 없다.

* 코틀린의 가시성 변경자와 자바
    * 코틀린의 public, protected, private 변경자는 컴파일 된 자바 바이트 코드 안에서도 그대로 유지된다.
    * 컴파일 된 코틀린 선언의 가시성은 자바에서 똑같은 가시성을 사용해 선언한 경우와 같다.
    * private 클래스는 예외이다.
    * 자바에서 클래스는 private으로 만들 수 없다.
    * 내부적으로 코틀린은 private 클래스를 패키지-전용 클래스로 컴파일한다.
    * internal 변경자는 자바에 딱 맞는 가시성이 없다.
    * internal 변경자는 바이트 코드 상에서는 public이 된다.
    * 다른 모듈에 정의된 internal 클래스나 internal 최상위 선언은 모듈 외부의 자바 코드에서 접근할 수 있다.
    * 코틀린에서 protected로 정의한 멤버를 코틀린 클래스와 같은 패키지의 자바 코드에서 접근할 수 있다.
    * 코틀린 컴파일러는 internal 가시성 멤버의 이름을 보기 나쁘게 변경한다.(mangle)

#### 4.1.4. 내부 클래스와 중첩된 클래스

* 자바처럼 코틀린에서도 클래스 안에 다른 클래스를 선언할 수 있다.
* 클래스 안에 다른 클래스를 선언하면 도우미 클래스를 캡슐화하거나 코드 정의를 그 코드를 사용하는 곳에 가까이 두고 싶을 때 유용하다.
* 자바와의 차이는 코틀린의 중첩 클래스(nested class)는 명시적으로 요청하지 않는 한 바깥쪽 클래스 인스턴스에 대한 접근 권한이 없다는 점이다.

* 자바 코드의 경우 중첩 클래스의 직렬화는 다음과 같은 문제가 있다.
    * Button 클래스에 대한 NotSerializableException 발생
    * 자바는 클래스 안에 정의한 클래스는 외부 클래스에 대한 참조를 묵시적으로 포함한다.
    * ButtonState를 직렬화하려면 Button을 직렬해야하므로 에러가 발생한다.
    * 이 문제를 해결하려면 static 클래스로 선언해야 한다.

```java
public class Button implements View {

    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(State state) { /* ... */ }

    public class ButtonState implements State { /* ... */ }
}
```

* 코틀린 코드는 중첩 클래스에 아무런 변경자가 붙지 않으면 자바 static 중첩 클래스와 같다.
    * 내부 클래스로 변경하여 바깥쪽 클래스에 대한 참조를 포함하게 만들고 싶은 경우 inner 변경자를 사용한다.

| 클래스 B 안에 정의된 클래스 A | 자바 | 코틀린 | 
|:-|:-|:-|
|중첩 클래스(바깥쪽 클래스에 대한 참조를 저장하지 않음)|static class A|class A|
|내부 클래스(바깥쪽 클래스에 대한 참조를 저장함)|class A|inner class A|

* 코틀린 바깥쪽 클래스의 인스턴스를 가리키는 참조를 표기하는 방법도 자바와 다르다.
    * this@Outer 라고 작성한다.

```kotlin
package blog.`in`.action.d

class Outer {

    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
```

#### 4.1.5. 봉인된 클래스

* 반드시 else 분기 처리가 필요하다.
* else에 반환할만한 값이 없으므로 예외를 던진다.

```kotlin
package blog.`in`.action.e

interface NotSealedExpr
class Num(val value: Int) : NotSealedExpr
class Sum(val left: NotSealedExpr, val right: NotSealedExpr) : NotSealedExpr

fun notSealedEval(expr: NotSealedExpr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> notSealedEval(expr.left) + notSealedEval(expr.right)
        else ->
            throw IllegalArgumentException("unknown expression")
    }
```

* 항상 디폴트 분기를 추가하는 것은 쉽지 않다.
* 디폴트 분기가 있으면 클래스 계층에 새로운 하위 클래스가 추가되더라도 컴파일러가 모든 경우를 처리하는지 제대로 알 수 없다.
* 새로운 클래스 처리를 잊어버렸더라도 디폴트 분기가 선택되기 때문에 버그가 발생할 수 있다.

* 코틀린은 sealed 클래스를 통해 이런 문제를 해결한다.
* 상위 클래스에 sealed 변경자를 붙이면 그 상위 클래스를 상속한 하위 클래스 정의를 제한할 수 있다.
* sealed 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩시켜야 한다.
* when 식에서 sealed 클래스의 모든 하위 클래스를 처리한다면 디폴트 분기가 필요없다.
* sealed로 표시된 클래스는 자동으로 open이다.
* 봉인된 클래스는 클래스 외부에 자신을 상속한 클래스를 둘 수 없다.
* sealed 클래스에 속한 값에 대해 디폴트 분기를 사용하지 않고 when 식을 사용하면 나중에 sealed 클래스의 상속 계층에 새로우 하위 클래스를 추가했을 때 컴파일 에러가 발생한다. 이는 when 식을 고쳐야 한다는 사실을 쉽게 파악할 수 있다.

```kotlin
package blog.`in`.action.e

sealed class Expr { // 기반 클래스를 sealed 봉인
    class Num(val value: Int) : Expr() // 기반 클래스의 모든 하위 클래스를 중첩 클래스로 나열한다.
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(expr: Expr): Int =
    when (expr) {
        is Expr.Num -> expr.value
        is Expr.Sum -> eval(expr.left) + eval(expr.right)
    }
```

* sealed 인터페이스는 정의할 수 없다.
    * 봉인된 인터페이스를 자바 쪽에서 구현하지 못하게 막을 수 있는 방법이 없기 때문이다.
* 코틀린 1.0에서 sealed는 제약이 심했다. 
    * 하위 클래스는 중첩 클래스로 정의되어야 했다.
    * 데이터 클래스는 sealed 클래스를 상속할 수도 없다.
* 코틀린 1.5부터는 봉인된 클래스가 정의된 패키지 안의 아무 위치(최상단, 다른 클래스, 객체, 인터페이스에 내포된 위치)에 선언할 수 있다.
    * 봉인된 인터페이스도 추가되었다.

### 4.2. 뻔하지 않은 생성자와 프로퍼티를 갖는 클래스 선언

* 코틀린도 자바처럼 생성자를 하나 이상 선언할 수 있다.
    * 주 생성자는 클래스를 초기화 할 때 주로 사용하는 간략한 생성자로 클래스 본문 밖에 선언한다.
    * 부 생성자는 클래스 본문 안에 선언한다.
* 초기화 블록을 통해 초기화 로직을 추가할 수 있다.

#### 4.2.1. 클래스 초기화: 주 생성자와 초기화 블록

* 보통 클래스의 모든 선언은 중괄호({}) 사이에 들어간다.
* 주 생성자는 중괄호가 없고 괄호 사이에 val 선언만 존재한다.
* 클래스 이름 뒤에 오는 괄호로 둘러싸인 코드를 주 생성자라고 부른다.
    * 파라미터를 지정한다. 
    * 생성자 파라미터에 의해 초기화되는 프로퍼티를 정의한다.

* constructor 키워드
    * 주 생성자나 부 생성자 정의를 시작할 때 사용
* init 키워드
    * 초기화 블럭을 시작한다.
    * 클래스의 객체가 만들어질 때(인스턴스화될 때) 실행될 초기화 코드가 들어간다.
    * 초기화 블록은 주 생성자와 함께 사용된다.
    * 주 생성자는 제한적이기 때문에 별도의 코드를 포함할 수 없으므로 초기화 블록이 필요하다.
    * 필요한 경우 클래스 안에 여러 초기화 블록을 선언할 수 있다.

```kotlin
package blog.`in`.action.f

class User constructor(_nickname: String) { // 파라미터가 한 개만 있는 생성자

    val nickname: String

    init { // 초기화 블럭
        nickname = _nickname
    }
}
```

* 다음에 나오는 생성자 선언 방식은 모두 같은 방식이다.

```kotlin
class User(_nickname: String) {
    val nickname = _nickname;
}
```

```kotlin
class User(val nickname: String)
```

* 함수 파라미터와 마찬가지로 생성자 파라미터에도 디폴트 값을 지정할 수 있다.

```kotlin
class User(val nickname: String, val isSubscribed: Boolean = false)

fun main() {
    val jun = User("jun")
    println(jun.nickname)
    println(jun.isSubscribed)
}
```

* 모든 생성자 파라미터에 디폴트 값을 지정하면 컴파일러가 자동으로 파라미터가 없는 생성자를 만든다.
* 그렇게 자동으로 만들어진 파라미터 없는 생성자는 디폴트 값을 사용해 클래스를 초기화한다.
* 의존성 주입(DI, Dependency Injection) 프레임워크 등 자바 라이브러리 중에 파라미터가 없는 생성자를 통해 객체를 생성해야만 라이브러리 사용이 가능한 경우가 있다.
* 코틀린이 제공하는 파라미터 없는 생성자는 그런 라이브러리와의 통합을 쉽게 돕는다.

* 클래스에 기반 클래스가 있다면 주 생성자는 기반 클래스의 생성자를 호출할 필요가 있다.
* 기반 클래스를 초기화하려면 기반 클래스 이름 뒤에 괄호를 치고 생성자 인자를 넘긴다.

```kotlin
open class User(val nickname: String, val isSubscribed: Boolean = false)

class TwitterUser(nickname: String): User(nickname)
```

* 클래스를 정의할 때 별도로 생성자를 정의하지 않으면 컴파일러가 자동으로 아무 일도 하지 않는 인자가 없는 디폴트 생성자를 만든다.
* Button의 생성자는 아무 인자도 받지 않지만, Button 클래스를 상속한 하위 클래스는 반드시 Button 클래스의 생성자를 호출해야 한다.
    * 이 규칙으로 기반 클래스의 이름 뒤에는 반드시 빈 괄호가 들어간다.
* 반면 인터페이스는 생성자가 없기 때문에 어떤 클래스가 인터페이스를 구현하더라고 괄호가 필요하지 않는다.

```kotlin
package blog.`in`.action.f

open class Button

class RadioButton : Button()
```

* 클래스 외부에서 인스턴스화하지 못하게 막으려면 모든 생성자를 private으로 만든다.
* 주 생성자가 비공개이므로 외부에서 Secretive를 인스턴스화 할 수 없다.

```kotlin
package blog.`in`.action.f

class Secretive private constructor() // 이 클래스의 유일한 주 생성자는 비공개
```

#### 4.2.2. 부 생성자: 상위 클래스를 다른 방식으로 초기화

* 일반적으로 코틀린에서 생성자가 여럿 있는 경우가 자바보다 훨씬 적다.
* 자바에서 오버로드한 생성자가 필요한 상황 중 상당수는 코틀린의 디폴트 파라미터 값과 이름 붙인 인자 문법을 사용해 해결할 수 있다.
    * 인자에 대한 디폴트 값을 제공하기 위해 부 생성자를 여럿 만들지 말라.
    * 파라미터 디폴트 값을 시그니처 직접 명시해라.

```kotlin
package blog.`in`.action.f

import javax.naming.Context
import javax.swing.text.AttributeSet

open class View {

    val context: Context
    val attributeSet: AttributeSet?

    constructor(context: Context) { // 부 생성자
        this.context = context
        this.attributeSet = null
    }

    constructor(context: Context, attributeSet: AttributeSet) { // 부 생성자
        this.context = context
        this.attributeSet = attributeSet
    }
}

class MyButton : View {

    constructor(context: Context) : super(context) { // 부 생성자

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) { // 부 생성자
        
    }
}
```

* 자바와 마찬깆로 생성자에서 this()를 통해 클래스 자신의 다른 생성자를 호출할 수 있다.

```kotlin
package blog.`in`.action.f

import javax.naming.Context
import javax.swing.text.AttributeSet

open class View {

    val context: Context
    val attributeSet: AttributeSet?

    constructor(context: Context) { // 부 생성자
        this.context = context
        this.attributeSet = null
    }

    constructor(context: Context, attributeSet: AttributeSet?) { // 부 생성자
        this.context = context
        this.attributeSet = attributeSet
    }
}

class MyButton : View {

    constructor(context: Context) : this(context, null) { // 다른 생성자에게 위임한다.

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) { // 부 생성자

    }
}
```

* 클래스에 주 생성자가 없다면 모든 부 생성자는 반드시 상위 클래스를 초기화하거나 다른 생성자에게 생성을 위임한다.
* 각 부 생성자에서 객체 생성을 위임하는 화살표를 따라가면 상위 클래스 생성자를 호출하는 화살표가 있어야 한다.
* 부 생성자가 필요한 주된 이유는 자바 상호 운용성이다.

#### 4.2.3. 인터페이스에 선언된 프로퍼티 구현

* 코틀린에서는 인터페이스에 추상 프로퍼티 선언을 넣을 수 있다.
* 아래 User 인터페이스를 구현하는 클래스는 nickname의 값을 얻을 수 있는 방법을 제공해야 한다는 의미이다.
    * 인터페이스는 아무 상태도 포함할 수 없으므로 상태를 저장할 필요가 있다면 인터페이스를 구현한 하위 클래스에서 상태 저장을 위한 프로퍼티 등을 만들어야 한다.

```kotlin
package blog.`in`.action.h

interface User {
    val nickname: String
}
```

* PrivateUser 클래스
    * 주 생성자 안에 프로퍼티를 직접 선언하는 간결한 구문 사용
    * 프로퍼티가 User 인터페이스의 추상 프로퍼티를 구현하고 있음을 override로 표시
* SubscribingUser 클래스
    * 커스텀 게터(getter)를 사용
    * 필드 값을 저장하지 않고 매번 이메일 주소에서 별명을 계산한다.
* FacebookUser 클래스
    * 초기화 식으로 nickname 값을 초기화한다.
    * getFacebookName 함수는 페이스북 인증을 거친 후 원하는 데이터를 가져와야 하기 때문에 비용이 많이 들 수 있다.
    * 객체를 초기화하는 단계에 한 번만 호출하도록 설계한다.

```kotlin
package blog.`in`.action.h

class PrivateUser(override val nickname: String) : User // 주 생성자에 있는 프로퍼티

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore("@") // 커스텀 getter
}

class FacebookUser(val accountId: Int) : User {

    override val nickname = getFacebookName(accountId) // 프로퍼티 초기화 식

    private fun getFacebookName(accountId: Int): String {
        return ""
    }
}
```

* 인터페이스에는 추상 프로퍼티뿐 아니라 게터와 세터가 있는 프로퍼티를 선언할 수도 있다.
    * email 프로퍼티는 반드시 오버라이드해야 한다.
    * nickname 프로퍼티는 오버라이드하지 않고 상속할 수 있다.

```kotlin
interface User {
    val email: String
    val nickname: String
        get() = email.substringBefore("@")
}
```

#### 4.2.4. 게터와 세터에서 뒷받침하는 필드에 접근

* 값을 저장하는 동시에 로직을 실행할 수 있게 하기 위해서는 접근자 안에서 프로퍼티를 뒷받침하는 필드에 접근할 수 있어야 한다.
* 코틀린에서 프로퍼티의 값을 바꿀 때는 user.address = "new value"처럼 필드 설정 구문을 사용한다.
    * 내부적으로 address의 세터를 호출한다.
* 접근자의 본문에서는 field라는 특별한 식별자를 통해 뒷받침하는 필드에 접근할 수 있다.
    * 게터에서 field 값을 읽을 수만 있고, 세터에서는 field 값을 읽거나 쓸 수 있다.

```kotlin
package blog.`in`.action.j

class CustomUser(val name: String) {

    var address: String = "unspecified"
        set(value: String) {
            println(
                """
                Address was changed for $name
                "$field" -> "$value"
            """.trimIndent()
            )
            field = value
        }
}

fun main() {

    val user = CustomUser("Alice")
    user.address = "Elsenheimerstrasse 47,80687 Muenchen"
}
```

* 컴파일러는 디폴트 접근자 구현을 사용하건 직접 게터나 세터를 정의하건 관계없이 게터나 세터에서 field를 사용하는 프로퍼티에 대해 뒷받침하는 필드를 생성해준다.
* field를 사용하지 않는 커스텀 접근자 구현을 정의하면 뒷받침하는 필드가 존재하지 않는다.
    * val 인 경우에는 게터에 없다.
    * var 인 경우에는 게터, 세터에 모두 없다.

#### 4.2.5. 접근자의 가시성 변경

* 접근자의 가시성은 기본적으로 프로퍼티 가시성과 같다.
* get 이나 set 앞에 가시성 변경자를 추가해서 접근자의 가시성을 변경할 수 있다.

* 아래 코드는 세터를 비공개로 설정한 코드이다.
* 클래스 밖에서 private set 설정된 프로퍼티를 변경할 수 없다.

```kotlin
package blog.`in`.action.j

class LengthCounter {

    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}

fun main() {

    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi")
    println(lengthCounter.counter)
}
```

### 4.3. 컴파일러가 생성한 메소드: 데이터 클래스와 클래스 위임

* 자바 플랫폼은 클래스가 equals, hashCode, toString 등의 메소드를 구현해야 한다. 
* 코틀린 컴파일러는 이런 메소드들을 기계적으료 생성하는 작업을 보이지 않는 곳에서 해준다.
    * 필수 메소드로 인한 잡음 없이 소스 코드를 깔끔하게 유지할 수 있다.

#### 4.3.1. 모든 클래스가 정의해야 하는 메소드

* 자바와 마찬가지로 toString, equals, hashCode 등 오버라이드 가능하다.

```kotlin
package blog.`in`.action.k

class Client(val name: String, val postalCode: Int) 
```

##### 문자열 표현
    
* 자바처럼 코틀린의 모든 클래스도 인스턴스의 문자열 표현을 얻을 방법을 제공한다.
* 기본 제공되는 객체의 문자열 표현은 Client@5e9f23b4 같은 방식이다.
* 기본 구현을 변경하려면 toString 메소드를 오버라이드해야 한다.

```kotlin
package blog.`in`.action.k

class Client(val name: String, val postalCode: Int) {

    override fun toString(): String {
        return "Client(name=$name, postalCode=$postalCode)"
    }
}

fun main() {

    val client = Client("junhyunny", 12345)
    println(client) // Client(name=junhyunny, postalCode=12345)
}
```

##### 객체의 동등성

* Client 클래스를 사용하는 모든 계산은 클래스 밖에서 이뤄진다.
* Client는 단지 데이터를 저장할 뿐이며 그에 따라 구조도 단순하고 내부 정보를 투명하게 외부에 노출한다.
* 클래스는 단순할지라도 동작에 대한 몇 가지 요구 사항이 있을 수 있다.
* 아래 예시 코드에서 두 객체는 동일한 값을 가지지만, 동일하지 않다.
* 두 객체를 동일하다고 판단하게 만들려면 equals 메소드를 오버라이드해야 한다.

```kotlin
fun main() {

    val client = Client("junhyunny", 12345)
    val secondClient = Client("junhyunny", 12345)
    println(client == secondClient) // false
}
```

* 동등성 연산에 == 사용
    * 자바는 equals 메소드를 사용한다.
    * 코틀린은 == 연산자를 사용하면 내부적으로 equals 메소드를 호출하여 객체를 비교한다.
    * 클래스가 equals 메소드를 오버라이드하면 == 연산자를 통해 안전하게 클래스의 인스턴스를 비교할 수 있다.
    * 코틀린에서 참조 비교를 위해서는 === 연산자를 사용한다.

```kotlin
package blog.`in`.action.k

class Client(val name: String, val postalCode: Int) {

    override fun toString(): String {
        return "Client(name=$name, postalCode=$postalCode)"
    }

    override fun equals(other: Any?): Boolean { // Any는 자바의 Object에 대응, Any? 는 널이 될 수 있다는 의미
        if (other == null || other !is Client) { // 널인지 Client 인스턴스인지 확인
            return false
        }
        return name == other.name && // 두 객체의 프로퍼티 값이 같은지 확인
                postalCode == other.postalCode
    }
}

fun main() {

    val client = Client("junhyunny", 12345)
    val secondClient = Client("junhyunny", 12345)
    println(client == secondClient) // true
}
```

* 코틀린의 is 검사는 자바의 instanceof 기능과 동일하다.
* is는 어떤 값의 타입인지 검사한다.

* 코틀린에서는 override 변경자가 필수이므로 실수로 override fun equals(other: Client)를 작성할 수 없다.
* equals 메소드를 잘 오버라이드하더라도 hashCode 정의를 정상적으로 동작하지 않을 수 있다. 

##### 해시 컨테이너: hashCode()

* 자바에서는 equals 메소드를 오버라이드할 때 hashCode 메소드도 함께 오버라이드해야 한다.
* JVM 언어에서 equals 메소드 비교가 true인 두 객체는 반드시 같은 hashCode를 반환한다는 규칙이 있다.

```kotlin
package blog.`in`.action.k

class Client(val name: String, val postalCode: Int) {

    override fun toString(): String {
        return "Client(name=$name, postalCode=$postalCode)"
    }

    override fun equals(other: Any?): Boolean { // Any는 자바의 Object에 대응, Any? 는 널이 될 수 있다는 의미
        if (other == null || other !is Client) { // 널인지 Client 인스턴스인지 확인
            return false
        }
        return name == other.name && // 두 객체의 프로퍼티 값이 같은지 확인
                postalCode == other.postalCode
    }

    override fun hashCode(): Int {
        return name.hashCode() * 31 + postalCode
    }
}

fun main() {

    val client = Client("junhyunny", 12345)
    val secondClient = Client("junhyunny", 12345)
    println(client == secondClient)

    val clientSet = hashSetOf(client)
    println(clientSet.contains(secondClient)) // true after hashcode override
}
```

#### 4.3.2. 데이터 클래스: 모든 클래스가 정의해야 하는 메소드 자동 생성

* 어떤 클래스가 데이터를 저장하는 역할을 수행하면 toString, equals, hashCode를 반드시 오버라이드해야 한다.
* 코틀린은 편안한하게 data라는 변경자를 클래스 앞에 붙인다.
    * data가 앞에 붙으면 데이터 클래스라고 부른다.
* 컴파일러가 자동으로 만들어준다.
* 자바에서 요구하는 모든 메소드를 포함한다.
    * 인스턴스 간 비교를 위한 equals
    * HashMap 같은 해시 기반 컨테이너에서 키로 사용할 수 있는 hashCode
    * 클래스의 각 필드를 선언 순서대로 표시하는 문자열을 만들어주는 toString
    * equals와 hashCode는 주 생성자에 나열된 모든 프로퍼티를 고려해서 만들어진다.
    * 생성된 equals 메소드는 모든 프로퍼티 값의 동등성을 확인한다.
    * hashCode 메소드는 모든 프로퍼티의 해시 값을 바탕으로 계산한 해시 값을 반환한다.
    * 생성자 밖에 정의된 프로퍼티는 equals / hashCode를 계산할 때 고려의 대상이 아니다.

```kotlin
package blog.`in`.action.k

data class Client(val name: String, val postalCode: Int) {

// not things to need
//    override fun toString(): String {
//        return "Client(name=$name, postalCode=$postalCode)"
//    }
//
//    override fun equals(other: Any?): Boolean { // Any는 자바의 Object에 대응, Any? 는 널이 될 수 있다는 의미
//        if (other == null || other !is Client) { // 널인지 Client 인스턴스인지 확인
//            return false
//        }
//        return name == other.name && // 두 객체의 프로퍼티 값이 같은지 확인
//                postalCode == other.postalCode
//    }
//
//    override fun hashCode(): Int {
//        return name.hashCode() * 31 + postalCode
//    }
}

fun main() {

    val client = Client("junhyunny", 12345)
    val secondClient = Client("junhyunny", 12345)
    println(client == secondClient)

    val clientSet = hashSetOf(client)
    println(clientSet.contains(secondClient))
}
```

##### 데이터 클래스와 불변셩: copy() 메소드

* 데이터 클래스의 프로퍼티가 반드시 val일 필요는 없다. 필요하다면 var 프로퍼티도 가능하다.
* 하지만 데이터 클래스의 모든 프로퍼티를 읽기 전용으로 만들어서 데이터 클래스를 불변(immutable) 클래스로 만들라고 권장한다.
* HashMap 등의 컨테이너에 데이터 클래스 객체를 담는 경우엔 불변성이 필수적이다.
    * 데이터 클래스 객체를 키로 사용하는 경우 데이터 클래스 객체의 값을 바꾸면 컨테이너 상태가 잘못될 수 있다.
* 다중 스레드를 사용하는 경우 불변성은 더 중요하다.
    * 객체의 상태 변경을 막으면 스레드를 동기화할 필요를 줄일 수 있다.
* copy 메소드는 해당 객체를 복사하면서 일부 프로퍼티를 바꿀 수 있게 돕는다.
* 객체를 직접 바꾸는 것이 아닌 복사본을 만드는 편이 더 낫다.
* 복사본은 원본과 다른 생명 주기를 가지며 복사를 하면서 일부 프로퍼티 값을 바꾸거나 복사본을 제거해도 프로그램에서 원본을 참조하는 다른 부분에 전혀 영향을 끼치지 않는다.
* copy 메소드를 직접 구현하면 다음과 같다.

```kotlin
class Client(val name: String, val postalCode: Int) {

    fun copy(name: String = this.name, postalCode: Int = this.postalCode) = Client(name, postalCode)
}
```

#### 4.3.3. 클래스 위임: by 키워드 사용

