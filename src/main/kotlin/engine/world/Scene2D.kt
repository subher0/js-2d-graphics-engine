package engine.world

import engine.graphics.Painter
import engine.graphics.objects.Drawable
import kotlin.js.Date

abstract class Scene2D<T> where T : Drawable {
    protected var painter: Painter? = null

    internal abstract fun initialise()
    protected abstract fun drawables(): List<T>
    protected abstract fun doAdvance(milliseconds: Int)

    // returns milliseconds it ran
    open fun advance(milliseconds: Int): Int {
        val start = Date()
        doAdvance(milliseconds)
        drawables().forEach { painter!!.addToQueue(it) }
        painter!!.redraw()
        val end = Date()
        return (end.getTime() - start.getTime()).toInt()
    }

    internal fun setPainter(painter: Painter) {
        this.painter = painter
    }
}