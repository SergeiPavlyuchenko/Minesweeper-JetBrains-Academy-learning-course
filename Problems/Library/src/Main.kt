import java.util.*

object Math {
    fun abs(input: Int): Int {
        return if (input < 0) -input else input
    }

    fun abs(input: Double): Double {
        return if (input < 0.0) -input else input
    }
}
