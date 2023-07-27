package blog.`in`.action.n

//class User {
//
//    val nickName: String
//
//    constructor(email: String) {
//        this.nickName = email.substringBefore("@")
//    }
//
//    constructor(facebookAccountId: Int) {
//        nickName = getFacebookName(facebookAccountId)
//    }
//
//    private fun getFacebookName(facebookAccountId: Int): String {
//        return "facebook-user"
//    }
//}

class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) = User(email.substringBefore("@"))
        fun newFacebookUser(facebookId: Int) = User(getFacebookName(facebookId))
        private fun getFacebookName(facebookId: Int): String {
            return "facebook-user-$facebookId"
        }
    }

    override fun toString(): String {
        return "(nickname = ${nickname})"
    }
}

fun main() {
    println(User.newSubscribingUser("junhyunny@gmail.com"))
    println(User.newFacebookUser(1))
}