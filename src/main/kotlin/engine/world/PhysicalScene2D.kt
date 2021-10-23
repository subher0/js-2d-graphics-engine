package engine.world

import engine.graphics.objects.Drawable
import engine.physics.VisibleObject
import kotlin.js.Date

abstract class PhysicalScene2D<T> : Scene2D<T>() where T : VisibleObject<out Drawable>  {

    // returns milliseconds it ran
    override fun advance(milliseconds: Int): Int {
        val start = Date()
        super.advance(milliseconds)
        drawables().forEach {
            it.move(milliseconds)
        }
        return (Date().getTime() - start.getTime()).toInt()
    }
}