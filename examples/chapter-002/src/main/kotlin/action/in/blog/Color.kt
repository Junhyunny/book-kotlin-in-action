package action.`in`.blog

enum class Color(
    val red: Int, val green: Int, val blue: Int // 상수의 프로퍼티를 정의한다
) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238); // 세미콜론 필수

    fun rgb() = (red * 256 + green) * 256 + blue // enum 클래스 안에서 메소드를 정의한다
}

fun getMnemonic(color: Color) =
    when (color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }

fun getWarmth(color: Color) =
    when (color) {
        Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
        Color.GREEN -> "neutral"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
//        else -> "other"
    }

fun mix(color1: Color, color2: Color) =
    when (setOf(color1, color2)) {
        setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
        setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
        setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
        else -> throw RuntimeException("Dirty Color")
    }

fun mixOptimized(color1: Color, color2: Color) =
    when {
        (color1 === Color.RED && color2 === Color.YELLOW) || (color1 == Color.YELLOW && color2 == Color.RED) -> Color.ORANGE
        (color1 === Color.YELLOW && color2 === Color.BLUE) || (color1 == Color.BLUE && color2 == Color.YELLOW) -> Color.GREEN
        (color1 === Color.BLUE && color2 === Color.VIOLET) || (color1 == Color.VIOLET && color2 == Color.BLUE) -> Color.INDIGO
        else -> throw RuntimeException("Dirty Color")
    }

fun main(args: Array<String>) {

    println(getMnemonic(Color.BLUE))
    println(getWarmth(Color.VIOLET))

    println(mix(Color.RED, Color.YELLOW))
    println(mixOptimized(Color.YELLOW, Color.RED))
}
