package game

import engine.world.SceneManager
import game.scenes.ExperimentalScene
import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement

fun main() {
    SceneManager.initialise(document.getElementById("canvas") as HTMLCanvasElement)
    SceneManager.getInstance().loadScene(ExperimentalScene())
}