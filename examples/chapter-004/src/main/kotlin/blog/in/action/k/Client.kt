package blog.`in`.action.k

data class Client(val name: String, val postalCode: Int) {

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