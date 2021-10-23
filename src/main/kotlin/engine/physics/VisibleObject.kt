package engine.physics

import engine.common.Vector2
import engine.graphics.objects.Drawable
import org.w3c.dom.CanvasRenderingContext2D

abstract class VisibleObject<T>(
    origin: Vector2,
    speed: Vector2,
    protected var representation: T
) : PhysicalObject(speed, origin), Drawable where T : Drawable {

    override fun draw(context: CanvasRenderingContext2D) {
        representation.draw(context)
    }

    override fun getLayer(): Int {
        return representation.getLayer()
    }

    override fun setOrigin(vector: Vector2) {
        representation.setOrigin(vector)
    }
}