package engine.graphics.objects

import engine.common.Vector2
import org.w3c.dom.CanvasRenderingContext2D

interface Drawable {
    fun draw(context: CanvasRenderingContext2D)
    fun getLayer(): Int
    fun setOrigin(vector: Vector2)
    fun getOrigin(): Vector2
}