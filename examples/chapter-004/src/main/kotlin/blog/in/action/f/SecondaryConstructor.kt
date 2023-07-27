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

    constructor(context: Context) : this(context, null) { // 부 생성자

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) { // 부 생성자

    }
}