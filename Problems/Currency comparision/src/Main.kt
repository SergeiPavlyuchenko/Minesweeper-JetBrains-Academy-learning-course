import java.util.Scanner
val sc = Scanner(System.`in`)

enum class Country(val country: String, val currency: String) {
    GERMANY("Germany", "Euro"),
    MALI("Mali", "CFA franc"),
    DOMINICA("Dominica", "Eastern Caribbean dollar"),
    CANADA("Canada", "Canadian dollar"),
    SPAIN("Spain", "Euro"),
    AUSTRALIA("Australia", "Australian dollar"),
    BRAZIL("Brazil", "Brazilian real"),
    SENEGAL("Senegal", "CFA franc"),
    FRANCE("France", "Euro"),
    GRENADA("Grenada", "Eastern Caribbean dollar"),
    KIRIBATI("Kiribati", "Australian dollar"),
    NULL("false", "false");

    companion object {

        fun compare(name: String): Country {
            for (i in values()) {
                if (i.country == name) return i
            }
            return NULL
        }
    }

}

fun main(args: Array<String>) {
    print(Country.compare(sc.next()).currency == Country.compare(sc.next()).currency)
}
