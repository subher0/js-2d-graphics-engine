package engine.graphics.objects

import engine.common.Vector2
import engine.graphics.Color
import engine.graphics.Color.Companion.BLACK
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.math.PI

class Circle(
    private var origin: Vector2,
    private var radius: Double,
    private var layer: Int = 0,
    var color: Color = BLACK,
) : AbstractDrawable(layer, origin) {

    override fun draw(context: CanvasRenderingContext2D) {
        context.fillStyle = color.toRgba()
        context.beginPath()
        context.arc(origin.getX(), origin.getY(), radius, 0.0, 2 * PI)
        context.fill()
        context.closePath()
    }

    fun getRadius() = radius

    fun setRadius(radius: Double) {
        this.radius = radius
    }

    override fun getOrigin(): Vector2 {
        return origin
    }
}