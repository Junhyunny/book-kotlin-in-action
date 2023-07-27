package blog.`in`.action.d

class Outer {

    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}