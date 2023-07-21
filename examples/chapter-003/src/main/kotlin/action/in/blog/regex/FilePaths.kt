package action.`in`.blog.regex

fun parsePath(path: String) {
//    val directory = path.substringBeforeLast("/")
//    val fullName = path.substringAfterLast("/")
//    val fileName = fullName.substringBeforeLast(".")
//    val extension = fullName.substringAfterLast(".")
//    println("Dir: $directory, name: $fileName, ext: $extension")

    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, fileName, extension) = matchResult.destructured
        println("Dir: $directory, name: $fileName, ext: $extension")
    }
}

fun main() {

    parsePath("/Users/yole/kotlin-book/chapter.adoc")
}