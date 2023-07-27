package blog.`in`.action.c

import blog.`in`.action.a.Focusable

internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey")
    protected fun whisper() = println("Let's talk")
}

// 아래 코드는 컴파일 에러가 발생한다.
//fun TalkativeButton.giveSpeech() { // public 멤버가 자신의 internal 수신 타입인 TalkativeButton 을 노출한다.
//    yell() // public 확장 함수는 private yell 함수에 접근할 수 없다.
//    whisper() // public 확장 함수는 protected whisper 함수에 접근할 수 없다.
//}