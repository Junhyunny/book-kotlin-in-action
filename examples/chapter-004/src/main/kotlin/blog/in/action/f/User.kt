package blog.`in`.action.f

//class User constructor(_nickname: String) { // 파라미터가 한 개만 있는 생성자
//
//    val nickname: String
//
//    init { // 초기화 블럭
//        nickname = _nickname
//    }
//}

open class User(val nickname: String, val isSubscribed: Boolean = false)

class TwitterUser(nickname: String) : User(nickname)

fun main() {
    val jun = User("jun")
    println(jun.nickname)
    println(jun.isSubscribed)
}