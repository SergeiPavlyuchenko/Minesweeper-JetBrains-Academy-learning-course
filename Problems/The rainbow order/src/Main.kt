import java.util.Scanner
enum class Rainbow(val color: String) {
    RED("Red"),
    ORANGE("Orange"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue"),
    INDIGO("Indigo"),
    VIOLET("Violet");

}



fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val color = input.next().toUpperCase()
    for (i in Rainbow.values()) {
    if (i == Rainbow.valueOf(color)) print(Rainbow.valueOf(color).ordinal + 1)
    }
}