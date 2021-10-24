package game.cosmos

import engine.EngineConfiguration
import engine.common.Vector2
import engine.graphics.Color
import engine.graphics.Color.Companion.WHITE
import engine.graphics.objects.Circle
import engine.physics.BoundingBox
import engine.physics.VisibleObject
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Star(
    origin: Vector2,
    speed: Vector2,
    radius: Double,
    color: Color,
    val isMeteor: Boolean,
    var tailLength: Double = 0.0
) : VisibleObject<Circle>(
    origin, speed,
    Circle(origin, radius, color = color)
) {
    private val originalRadius = representation.getRadius()
    private var numberOfDraws = 0


    override fun boundingBox(): BoundingBox {
        val radius = representation.getRadius()
        return BoundingBox(
            Vector2(origin.getX() - radius, origin.getY() - radius),
            Vector2(radius * 2, radius * 2)
        )
    }

    override fun getOrigin(): Vector2 {
        return representation.getOrigin()
    }

    override fun draw(context: CanvasRenderingContext2D) {
        numberOfDraws += 1
        val scalar = max(
            abs(origin.getX() - EngineConfiguration.getInstance().center().getX()),
            abs(origin.getY() - EngineConfiguration.getInstance().center().getY())
        )
        representation.setRadius(
            scalar / 2000 * originalRadius + min(
                200.0,
                if (isMeteor) numberOfDraws.toDouble() * 0.05 else 0.0
            )
        )
        super.draw(context)
        if (tailLength > 0.0) {
            val tailEndX = origin.getX() - representation.getRadius() * speed.getX() * tailLength
            val tailEndY = origin.getY() - representation.getRadius() * speed.getY() * tailLength
            val gradient = context.createLinearGradient(origin.getX(), origin.getY(), tailEndX, tailEndY)
            gradient.addColorStop(0.0, representation.color.toRgba())
            gradient.addColorStop(1.0, representation.color.change(alpha = 0.0f).toRgba())
            context.fillStyle = gradient
            context.beginPath()
            context.moveTo(origin.getX(), origin.getY() - representation.getRadius())
            context.lineTo(origin.getX(), origin.getY() + representation.getRadius())
            context.lineTo(tailEndX + representation.getRadius() / 2, tailEndY + representation.getRadius() / 2)
            context.lineTo(tailEndX - representation.getRadius() / 2, tailEndY - representation.getRadius() / 2)
            context.closePath()
            context.fill()
        }
    }

    fun setTailLength(tailLength: Double) {
        this.tailLength = tailLength
    }
}