import java.lang.Exception

class City(val name: String) {
    var degrees: Int = 0
        set(value) {
            field = if (value < -92 || value > 57) defaultDegrees else value
        }
    val defaultDegrees = when (name) {
        "Moscow" -> 5
        "Hanoi" -> 20
        "Dubai" -> 30
        "neither" -> Int.MIN_VALUE
        else -> throw Exception("Unknown City")
    }
}        

fun main() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai")
    firstCity.degrees = first
    val secondCity = City("Moscow")
    secondCity.degrees = second
    val thirdCity = City("Hanoi")
    thirdCity.degrees = third

    val coldest = minColdest(firstCity, minColdest(secondCity, thirdCity)).name
    print(coldest)

    /*val cityDegreesArray = arrayOf(firstCity, secondCity, thirdCity)
    var coldestTemp = Int.MAX_VALUE
    var coldestCity = ""
    var moreOneCityCount = 0
    for (i in cityDegreesArray.indices) {
        if (cityDegreesArray[i].degrees < coldestTemp) {
            coldestTemp = cityDegreesArray[i].degrees
            coldestCity = cityDegreesArray[i].name
        } else if (cityDegreesArray[i].degrees == coldestTemp) moreOneCityCount++
    }
    print(if (moreOneCityCount > 0) "neither" else coldestCity)*/
}

fun minColdest(firstCityCompare: City, secondCityCompare: City): City {
    return when {
        firstCityCompare.degrees < secondCityCompare.degrees -> firstCityCompare
        firstCityCompare.degrees > secondCityCompare.degrees -> secondCityCompare
        else -> City("neither")
    }
}
