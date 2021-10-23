package game.scenes

import engine.common.Vector2
import engine.graphics.Color
import engine.graphics.objects.Drawable
import engine.physics.PhysicalCircle
import engine.physics.VisibleObject
import engine.world.PhysicalScene2D

class ExperimentalScene : PhysicalScene2D<VisibleObject<out Drawable>>() {
    private val drawables = ArrayList<VisibleObject<out Drawable>>()

    override fun initialise() {
        val alpha = 0.7f
        for (i in 1..10000) {
            drawables.add(
                PhysicalCircle(
                    Vector2(800.0, 600.0),
                    Vector2((-1000..1000).random() / 100.0, (-1000..1000).random() / 100.0), (5..100).random().toDouble(),
                    Color((0..255).random(), (0..255).random(), (0..255).random(), (0..100).random() / 100f)
                )
            )
        }
    }

    override fun drawables(): List<VisibleObject<out Drawable>> {
        return drawables
    }

    override fun doAdvance(milliseconds: Int) {

    }
}