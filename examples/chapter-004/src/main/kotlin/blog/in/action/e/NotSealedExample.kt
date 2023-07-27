package blog.`in`.action.e

interface NotSealedExpr
class Num(val value: Int) : NotSealedExpr
class Sum(val left: NotSealedExpr, val right: NotSealedExpr) : NotSealedExpr

fun notSealedEval(expr: NotSealedExpr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> notSealedEval(expr.left) + notSealedEval(expr.right)
        else ->
            throw IllegalArgumentException("unknown expression")
    }
