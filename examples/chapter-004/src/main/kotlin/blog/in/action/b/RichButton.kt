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