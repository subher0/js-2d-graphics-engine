package engine.world

import engine.EngineConfiguration
import engine.common.delay
import engine.graphics.Painter
import engine.graphics.objects.Drawable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLCanvasElement
import kotlin.js.Date
import kotlin.math.max

class SceneManager private constructor(private var painter: Painter){
    private var scene: Scene2D<out Drawable>? = null
    private var swapSceneChannel: Channel<Scene2D<out Drawable>> = Channel(1)
    private var timePassedBetweenFramesMillis: Int = 0

    fun loadScene(scene: Scene2D<out Drawable>): SceneManager {
        if (this.scene == null) {
            this.scene = scene
            GlobalScope.launch {
                swapScenes(scene)
                loop()
            }
        } else {
            GlobalScope.launch {
                swapScenes(scene)
            }
        }
        return this
    }

    private suspend fun swapScenes(scene: Scene2D<out Drawable>) {
        scene.setPainter(painter)
        scene.initialise()
        swapSceneChannel.send(scene)
    }

    private suspend fun loop() {
        var millisAdvanced = 0
        while (true) {
            var start = Date()
            if (scene == null) {
                throw RuntimeException("Scene is not present!")
            }
            val result = swapSceneChannel.tryReceive()
            if (result.isSuccess) {
                this.scene = result.getOrThrow()
            }
            val timeTakenMs = scene!!.advance(timePassedBetweenFramesMillis)
            delay(max(EngineConfiguration.getInstance().millisPerFrame() - timeTakenMs, 0))
            timePassedBetweenFramesMillis = calculateAdvancementTime((Date().getTime() - start.getTime()).toInt())
            millisAdvanced += timePassedBetweenFramesMillis
            console.log(millisAdvanced)
        }
    }

    private fun calculateAdvancementTime(actualTime: Int): Int {
        if (EngineConfiguration.getInstance().simulationMode() == EngineConfiguration.SimulationMode.PRECISION) {
            return EngineConfiguration.getInstance().millisPerFrame()
        } else if (EngineConfiguration.getInstance().simulationMode() == EngineConfiguration.SimulationMode.BEST_EFFORT) {
            return actualTime
        }
        throw RuntimeException("Unknown simulation mode " + EngineConfiguration.getInstance().simulationMode())
    }

    companion object {
        private var painter: Painter? = null
        private var sceneManager: SceneManager? = null

        fun initialise(canvas: HTMLCanvasElement) {
            Painter.initialise(canvas, EngineConfiguration.getInstance().drawBoundingBoxes())
            painter = Painter.getInstance()
            sceneManager = SceneManager(painter!!)
        }

        fun getInstance(): SceneManager {
            return sceneManager!!
        }
    }
}