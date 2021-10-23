package engine

class EngineConfiguration() {
    fun drawBoundingBoxes() = false

    fun targetFps() = 60

    fun millisPerFrame() = 1000 / targetFps()

    fun simulationMode() = SimulationMode.BEST_EFFORT

    fun useDoubleBuffering() = false

    companion object {
        private val engineConfiguration = EngineConfiguration()

        fun getInstance() = engineConfiguration;
    }

    enum class SimulationMode {
        BEST_EFFORT,
        PRECISION
    }
}