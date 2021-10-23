package engine.graphics.objects

import engine.common.Vector2

abstract class AbstractDrawable(private var layer: Int, private var origin: Vector2) : Drawable {
    override fun getLayer(): Int {
        return layer
    }

    override fun setOrigin(vector: Vector2) {
        origin = vector
    }
}