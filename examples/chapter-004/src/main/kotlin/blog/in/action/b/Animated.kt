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
