package blog.`in`.action.h

class PrivateUser(override val nickname: String) : User // 주 생성자에 있는 프로퍼티

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore("@") // 커스텀 getter
}

class FacebookUser(accountId: Int) : User {

    override val nickname = getFacebookName(accountId) // 프로퍼티 초기화 식

    private fun getFacebookName(accountId: Int): String {
        return ""
    }
}