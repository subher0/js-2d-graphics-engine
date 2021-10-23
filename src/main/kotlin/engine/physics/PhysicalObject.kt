package engine.physics

import engine.common.Vector2
import engine.graphics.Color
import org.w3c.dom.CanvasRenderingContext2D

// Speed in pixels per seconds
abstract class PhysicalObject(protected var speed: Vector2,
                              protected var origin: Vector2) {
    abstract fun boundingBox(): BoundingBox

    fun getSpeed() = speed

    fun move(milliseconds: Int) {
        val moveBy = speed.copy()
        moveBy.scale(milliseconds.toDouble() / 1000.0)
        origin.plus(moveBy)
    }

    fun accelerate(acceleration: Vector2) {
        speed.plus(acceleration)
    }

    fun drawBoundingBox(context: CanvasRenderingContext2D) {
        context.fillStyle = null
        context.strokeStyle = Color.RED.toRgba()
        val bb = boundingBox()
        context.beginPath()
        context.rect(bb.origin.getX(), bb.origin.getY(), bb.size.getX(), bb.size.getY())
        context.stroke()
        context.closePath()
    }
}