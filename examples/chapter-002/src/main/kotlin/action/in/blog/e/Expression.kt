package action.`in`.blog.e

interface Expression
class Num(val value: Int) : Expression // value라는 프로퍼티만 존재하는 단순한 클래스, Expression을 구현
class Sum(val left: Expression, val right: Expression) : Expression // Expression 타입의 객체라면 어떤 것이나 Sum 연산의 인자가 될 수 있다.

fun eval(expression: Expression): Int {
    if (expression is Num) {
        val n = expression // 불필요한 중복 형변환
        return n.value
    }
    if (expression is Sum) {
        return eval(expression.left) + eval(expression.right)
    }
    throw IllegalArgumentException("unknown expression")
}

fun evalAsKotlinWithIf(expression: Expression): Int =
    if (expression is Num) expression.value
    else if (expression is Sum) evalAsKotlinWithIf(expression.left) + evalAsKotlinWithIf(expression.right)
    else throw IllegalArgumentException("unknown expression")

fun evalAsKotlinWithWhen(expression: Expression): Int =
    when (expression) {
        is Num -> expression.value
        is Sum -> evalAsKotlinWithWhen(expression.left) + evalAsKotlinWithWhen(expression.right)
        else -> throw IllegalArgumentException("unknown expression")
    }

fun evalWithLogging(expression: Expression): Int =
    when (expression) {
        is Num -> {
            println("num: ${expression.value}")
            expression.value
        }

        is Sum -> {
            val leftValue = evalWithLogging(expression.left)
            val rightValue = evalWithLogging(expression.right)
            println("sum: ${leftValue} + ${rightValue}")
            leftValue + rightValue
        }

        else -> throw IllegalArgumentException("unknown expression")
    }

fun main(args: Array<String>) {

    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalAsKotlinWithIf(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalAsKotlinWithWhen(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))
}