package engine.physics

import engine.common.Vector2
import engine.graphics.Color
import engine.graphics.objects.Circle

class PhysicalCircle(origin: Vector2, speed: Vector2, radius: Double, color: Color) : VisibleObject<Circle> (
    origin, speed,
    Circle(origin, radius, color = color)
) {
    override fun boundingBox(): BoundingBox {
        val radius = representation.getRadius()
        return BoundingBox(
            Vector2(origin.getX() - radius, origin.getY() - radius),
            Vector2(radius * 2, radius * 2)
        )
    }
}