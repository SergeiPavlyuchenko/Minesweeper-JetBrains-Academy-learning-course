package minesweeper
import java.util.*
import java.util.Random

val sc = Scanner(System.`in`)

enum class Mark(val symbol: Char) {
    Empty('.'),
    ThinkMine('*'),
    Mine('X');
}

enum class CountEqual (val digit: Int, val char: Char) {
    One(1,'1'),
    Two(2,'2'),
    Three(3,'3'),
    Four(4,'4'),
    Five(5,'5'),
    Six(6,'6'),
    Seven(7,'7'),
    Eight(8,'8');


    companion object {

        fun isChar(count: Int): Char {
            for (i in CountEqual.values()) {
                if(count == i.digit) return i.char
            }
            return '#'
        }

    }
}



class Location(val x: Int, val y: Int) {

    fun up() = Location(x, y -1)
    fun down() = Location(x, y + 1)
    fun left() = Location(x - 1, y)
    fun right() = Location(x + 1, y)

    fun store(): List<Int> {
        return listOf(x + 1, y + 1)
    }

}

fun Location.checkAround() : List<Location> {
    val location = this
    return listOf(
            location.up(), location.up().left(), location.up().right(),
            location.left(), location.right(),
            location.down().left(), location.down(), location.down().right()
    )
}

open class MainField(val height: Int, val width: Int, val mines: Int) {
    private val random = Random()

    fun setTheMines(): List<MutableList<Char>> {
        val field = MutableList(height) { MutableList(width) {'.'} }
        var countTotalMines = 0
        while (countTotalMines < mines) {
            val mineColumn = random.nextInt(height)
            val mineRow = random.nextInt(width)
            if (field[mineColumn][mineRow] != Mark.Mine.symbol) {
                field[mineColumn][mineRow] = Mark.Mine.symbol
                countTotalMines++
            }
        }
        return field.toList()
    }
}



class MinesField(height: Int, width: Int, mines: Int): MainField(height, width, mines) {


    fun isOutOfBound(location: Location, field: List<MutableList<Char>>): Boolean {
        if (location.y < 0 || location.y > field.lastIndex
                || location.x < 0 || location.x > field.lastIndex) return true
        return false
    }

    fun battlefield(field: List<MutableList<Char>>): List<MutableList<Char>> {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val location = Location(x, y)
                if (field[y][x] != Mark.Mine.symbol) {
                    var count = 0
                    for (around in location.checkAround()) {
                        if (!isOutOfBound(around, field) && field[around.y][around.x] == Mark.Mine.symbol) {
                            count++
                        }
                    }
                    field[y][x] = if (count == 0) Mark.Empty.symbol else CountEqual.isChar(count)
                }
            }
        }
        return field
    }

    fun howMuchMines(field: List<MutableList<Char>>): MutableList<List<Int>> {
        val minesList = mutableListOf<List<Int>>()
        for (i in field.indices) {
            for (j in field[i].indices) {
                val location = Location(j, i)
                if (field[i][j] == Mark.Mine.symbol) minesList.add(location.store())
            }
        }
        return minesList
    }


    object HiddenMines {

        fun hideTheMines(height: Int, width: Int, mines: Int, field: List<MutableList<Char>>): List<MutableList<Char>> {
            val unhiddenMines = MinesField(height, width, mines).battlefield(field)
            val hiddenMines = mutableListOf<MutableList<Char>>()
            var charList: List<Char>
            for (y in unhiddenMines.indices) {
                val tempList = mutableListOf<Char>()

                for (x in unhiddenMines[y].indices) {
                    if (unhiddenMines[y][x] == Mark.Mine.symbol) {
                        tempList.add(Mark.Empty.symbol)
                    } else tempList.add(unhiddenMines[y][x])
                }
                charList = tempList
                hiddenMines.add(charList)
            }
            return hiddenMines
        }


        fun locationMinesList(field: List<MutableList<Char>>): List<List<Int>> {
            val locationsMinesList = mutableListOf<List<Int>>()
            for (y in field.indices) {
                for (x in field[y].indices) {
                    val location = Location(x, y)
                    if (field[y][x] == Mark.Mine.symbol) {
                        locationsMinesList.add(location.store())
                    }
                }
            }
            return locationsMinesList.toList()
        }
    }

    object PlayerActionWithMark {

        fun whereIsNumbers (field: List<MutableList<Char>>): List<List<Int>> {
            val ifIntList = mutableListOf<List<Int>>()
            for (i in field.indices) {
                for (j in field[i].indices) {
                    val location = Location(j, i)
                    if (field[i][j].isDigit()) ifIntList.add(location.store())
                }
            }
            return ifIntList.toList()
        }

        fun setOrDelete(x: Int, y: Int, field: List<MutableList<Char>>, intList: List<List<Int>>): List<MutableList<Char>> {
            val err = mutableListOf<MutableList<Char>>()
            val str = ("There is a number here!").toMutableList()
            err.add(str)
            val setLocation = listOf(x, y)
            if (setLocation in intList) return err.toList()
            if (field[y - 1][x - 1] == Mark.Empty.symbol && !field[y - 1][x - 1].isDigit() ) field[y - 1][x - 1] = Mark.ThinkMine.symbol
            else field[y - 1][x - 1] = Mark.Empty.symbol
            return field
        }

        fun howMuchMarks(field: List<MutableList<Char>>): MutableList<List<Int>> {
            val thinkMinesMarksList = mutableListOf<List<Int>>()
            for (i in field.indices) {
                for (j in field[i].indices) {
                    val location = Location(j, i)
                    if (field[i][j] == Mark.ThinkMine.symbol) thinkMinesMarksList.add(location.store())
                }
            }
            return thinkMinesMarksList
        }

        fun minesEqualMarks( mines: MutableList<List<Int>>, marks: MutableList<List<Int>>): Boolean {
            if(marks.size == mines.size) {
                for (i in mines) {
                    for (j in marks) {
                        var count = 0
                        if (i == j) count++
                        if(count == mines.size) return true
                    }

                }
            }
            return false
        }

    }


    object Board {

        fun create(height: Int, width: Int, field: List<MutableList<Char>>): List<MutableList<Char>> {
            //top lines
            val boardField = mutableListOf<MutableList<Char>>()
            val firstLine = mutableListOf<Char>(); firstLine.add(' '); firstLine.add('?')
            val secondLine = mutableListOf<Char>(); secondLine.add('-'); secondLine.add('?')
            for (i in 0 until width) {
                firstLine.add((i + 1).toString().first())
                secondLine.add('-')
            }; firstLine.add('?'); secondLine.add('?')

            //middle lines
            boardField.add(firstLine)
            boardField.add(secondLine)
            var charList: List<Char>
            for (y in 0 until height) {
                val tempList = mutableListOf<Char>()
                tempList.add((y + 1).toString().first())
                tempList.add('?')
                for (x in 0 until width) {
                    tempList.add(field[y][x])
                }
                tempList.add('?')
                charList = tempList
                boardField.add(charList)
            }

            //bottom lines
            boardField.add(secondLine)
            return boardField
        }
    }
}


fun main() {
    print("How many mines do you want on the field? ")
    val mines = sc.nextInt()
    val height = 9
    val width = 9
    val mainField = MainField(height, width, mines).setTheMines()
    val hiddenField = MinesField.HiddenMines.hideTheMines(height,width,mines,mainField)
    val howMuchMines = MinesField(height, width, mines).howMuchMines(mainField)



    println(MinesField.Board.create(height, width, hiddenField).joinToString("\n") { it. joinToString("")})


    print("Set/delete mines marks (x and y coordinates): ")
    while (sc.hasNext()) {


        val whereIsNumbers = MinesField.PlayerActionWithMark.whereIsNumbers(hiddenField)
        val setAction = MinesField.PlayerActionWithMark.setOrDelete(sc.nextInt(), sc.nextInt(), hiddenField,whereIsNumbers)
        val howMuchMarks = MinesField.PlayerActionWithMark.howMuchMarks(setAction)


        println(if(setAction.size == 1) setAction.joinToString("\n") {it.joinToString("")} else
            MinesField.Board.create(height, width, setAction).joinToString("\n") {it.joinToString("")})
        if(MinesField.PlayerActionWithMark.minesEqualMarks(howMuchMines, howMuchMarks)) {
            println("Congratulations! You found all the mines!")
            return
        }
        print("Set/delete mines marks (x and y coordinates): ")

    }


}