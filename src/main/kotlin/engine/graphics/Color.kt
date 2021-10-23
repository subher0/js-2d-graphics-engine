package engine.graphics

import engine.common.format
import kotlin.math.sqrt

class Color(private val red: Int, private val green: Int, private val blue: Int, private val alpha: Float) {
    private var rgbaValue: String? = null;

    fun toRgba(): String {
        if (rgbaValue == null) {
            rgbaValue = "rgba($red,$green,$blue,${alpha.format(2)})"
        }
        return rgbaValue!!
    }

    fun change(red: Int? = null, green: Int? = null, blue: Int? = null, alpha: Float? = null): Color {
        return Color(red ?: this.red, green ?: this.green, blue ?: this.blue, alpha ?: this.alpha)
    }

    fun mix(color: Color): Color {
        return Color(
            (this.red + color.red) / 2,
            (this.green + color.green) / 2,
            (this.blue + color.blue) / 2,
            sqrt(this.alpha * color.alpha)
        )
    }

    companion object {
        val BLACK = Color(0, 0, 0, 1.0f)
        val WHITE = Color(255, 255, 255, 1.0f)
        val RED = Color(255, 0, 0, 1.0f)
        val GREEN = Color(0, 255, 0, 1.0f)
        val BLUE = Color(0, 0, 255, 1.0f)
    }
}