package game.cosmos

import engine.EngineConfiguration
import engine.common.Vector2
import engine.graphics.Color
import engine.graphics.objects.Drawable
import engine.physics.VisibleObject
import engine.world.PhysicalScene2D
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent
import kotlin.math.*

class CosmosScene : PhysicalScene2D<VisibleObject<out Drawable>>() {
    private val drawables = ArrayList<VisibleObject<out Drawable>>()
    private val spawnRadius = 5.0
    private val sprinkleSpeed = 0.05
    private val numberOfNozzles = 100
    private var speedFactorMax = 1.5
    private var speedFactorMin = 1.0
    private var speedGrowthFactor = -0.3

    private var speedFactor = speedFactorMin
    private var sprinklePosition = 0.0
    private var currentNozzle = 0

    private var tailLengthGrowthFactor = -0.05
    private var tailLength = 0.0

    override fun initialise() {
        val alpha = 0.7f
        window.addEventListener("keyup", {
            val keyboardEvent = it as KeyboardEvent
            if (keyboardEvent.code == "Enter") {
                console.log(keyboardEvent)
                tailLengthGrowthFactor = -tailLengthGrowthFactor
                speedGrowthFactor = -speedGrowthFactor
            }
        })
    }

    override fun drawables(): List<VisibleObject<out Drawable>> {
        return drawables
    }

    override fun doAdvance(milliseconds: Int) {
        console.log(drawables.size)
        tailLength = max(min(1.0, tailLength + tailLengthGrowthFactor), 0.0)
        speedFactor = max(min(speedFactorMax, speedFactor + speedGrowthFactor), speedFactorMin)
        sprinklePosition += sprinkleSpeed
        val ballsGenerated = (milliseconds / 5.0 * speedFactor / speedFactorMin).toInt()
        for (i in 0..ballsGenerated) {
            var radius = (1000..3000).random() / 1000.0
            if ((0..15).random() == 0)
               radius *= (3000..30000).random() / 1000.0
            val originAngle = randomAngle()
            val originX = spawnRadius * cos(originAngle) + EngineConfiguration.getInstance().center().getX()
            val originY = spawnRadius * sin(originAngle) + EngineConfiguration.getInstance().center().getY()
            val speedX = cos(originAngle) * min(radius * radius, 300.0)
            val speedY = sin(originAngle) * min(radius * radius, 300.0)
            drawables.add(
                Star(
                    Vector2(originX, originY),
                    Vector2(speedX, speedY), radius,
                    Color((240..255).random(), (240..255).random(), (240..255).random(), (90..100).random() / 100f),
                    (0..500).random() == -1,
                    tailLength
                )
            )
        }
        cleanUp()
    }

    private fun randomAngle(): Double {
        return (0..10000).random() / 10000.0 * 2 * PI
    }

    private fun nextNozzleAngle(): Double {
        currentNozzle = ((currentNozzle + 1) % numberOfNozzles)
        return currentNozzle.toDouble() / numberOfNozzles * 2 * PI + sprinklePosition
    }

    private fun cleanUp() {
        for (i in (drawables.size - 1) downTo 0) {
            var drawable = drawables.get(i)
            if (drawable is Star) {
                drawable.setTailLength(tailLength)
                drawable.multiplySpeed(speedFactor)
                drawable.multiplySpeed(
                    min(max(max(
                        abs(
                            drawable.getOrigin().getX() - EngineConfiguration.getInstance().center().getX()
                        ),
                        abs(
                            drawable.getOrigin().getY() - EngineConfiguration.getInstance().center().getY()
                        )
                    ) * 0.1, 1.0), 10.0))
            }
            if (drawable.getOrigin().getX() < -200 || drawable.getOrigin().getX() > 3000
                || drawable.getOrigin().getY() < -200 || drawable.getOrigin().getY() > 2000
            ) {
                drawables.removeAt(i)
            }
        }
    }
}