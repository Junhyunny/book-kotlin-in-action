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
