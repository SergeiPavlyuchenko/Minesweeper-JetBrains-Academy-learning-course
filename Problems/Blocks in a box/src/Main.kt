import java.lang.ArithmeticException

class Block(val color: String) {
    object DimProperties {
        var length = 100
        var width = 100

        fun blocksInBox(length: Int, width: Int): Int {
            return if (length > 0 && width > 0 ) (length / DimProperties.length) * (width / DimProperties.width )
            else throw ArithmeticException("Divine by zero or negative value")
        }
    }
}

/*fun main() {
    println(Block.DimProperties.blocksInBox(2,3))
}*/
