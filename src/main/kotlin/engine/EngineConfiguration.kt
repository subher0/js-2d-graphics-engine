package engine

import engine.common.Vector2

class EngineConfiguration() {
    fun drawBoundingBoxes() = false

    fun targetFps() = 60

    fun millisPerFrame() = 1000 / targetFps()

    fun simulationMode() = SimulationMode.BEST_EFFORT

    fun useDoubleBuffering() = false

    fun center() = Vector2(1300.0, 650.0)

    companion object {
        private val engineConfiguration = EngineConfiguration()

        fun getInstance() = engineConfiguration;
    }

    enum class SimulationMode {
        BEST_EFFORT,
        PRECISION
    }
}