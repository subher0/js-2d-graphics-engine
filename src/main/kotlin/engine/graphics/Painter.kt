package engine.graphics

import engine.EngineConfiguration
import engine.common.Vector2
import engine.graphics.objects.Drawable
import engine.physics.PhysicalObject
import kotlinx.browser.document
import org.w3c.dom.CanvasImageSource
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.PI

class Painter {
    private val drawables = HashMap<Int, MutableList<Drawable>>()
    private val context: CanvasRenderingContext2D
    private val canvas: HTMLCanvasElement
    private val drawBoundingBoxes: Boolean

    private val bufferCanvas: HTMLCanvasElement?
    private val bufferContext: CanvasRenderingContext2D?

    private var scale = Vector2(1.0, 1.0)
    private var origin = Vector2(0.0, 0.0)
    private var rotationAngle = 0.0

    private var previousScale = scale
    private var previousOrigin = origin
    private var previousRotationAngle = rotationAngle

    private constructor(
        context: CanvasRenderingContext2D,
        canvas: HTMLCanvasElement,
        drawBoundingBoxes: Boolean
    ) {
        this.context = context
        this.drawBoundingBoxes = drawBoundingBoxes
        this.canvas = canvas
        if (EngineConfiguration.getInstance().useDoubleBuffering()) {
            bufferCanvas = document.createElement("canvas") as HTMLCanvasElement
            bufferCanvas.width = canvas.width
            bufferCanvas.height = canvas.height
            bufferContext = bufferCanvas.getContext("2d") as CanvasRenderingContext2D
        } else {
            bufferContext = null
            bufferCanvas = null
        }
    }

    fun addToQueue(drawable: Drawable) {
        drawables.getOrPut(drawable.getLayer()) { ArrayList() }.add(drawable)
    }

    fun redraw() {
        if (bufferContext != null) {
            clearAll(bufferContext)
        }
        clearAll(context)
        val context = bufferContext ?: this.context
        ArrayList(drawables.keys).apply { this.sort() }.forEach {
            drawables[it]!!.forEach { drawable ->
                drawable.draw(context)
                if (drawBoundingBoxes)
                    if (drawable is PhysicalObject)
                        drawable.drawBoundingBox(context)
            }
        }
        transform()
        if (bufferContext != null) {
            this.context.drawImage(bufferCanvas as CanvasImageSource, origin.getX(), origin.getY())
        }

        drawables.clear()
    }

    fun scaleLinear(scale: Double) {
        this.scale = Vector2(this.scale.getX() * scale, this.scale.getY() * scale)
    }

    fun moveTo(coordinates: Vector2) {
        origin = coordinates
    }

    fun moveBy(coordinates: Vector2) {
        origin.plus(coordinates)
    }

    fun rotate(degrees: Double) {
        rotationAngle = degrees * PI / 180
    }

    private fun transform() {
        scale()
        rotate()
        translate()
    }

    private fun scale() {
        if (previousScale != scale) {
            if (bufferContext != null)
                bufferContext.scale(scale.getX(), scale.getY())
            context.scale(scale.getX(), scale.getY())
            previousScale = scale
        }
    }

    private fun rotate() {
        if (previousRotationAngle != rotationAngle) {
            if (bufferContext != null)
                bufferContext.rotate(rotationAngle - previousRotationAngle)
            context.rotate(rotationAngle - previousRotationAngle)
            previousRotationAngle = rotationAngle
        }
    }

    private fun translate() {
        if (previousOrigin != origin) {
            if (bufferContext != null)
                bufferContext.translate(origin.getX(), origin.getY())
            context.translate(origin.getX(), origin.getY())
            previousOrigin = origin
        }
    }

    private fun clearAll(context: CanvasRenderingContext2D) {
        context.save();

        context.setTransform(1.0, 0.0, 0.0, 1.0, 0.0, 0.0);
        context.clearRect(
            0.0,
            0.0,
            canvas.width * 1.0,
            canvas.height * 1.0
        )
        context.restore();
    }

    companion object {
        private var painterInstance: Painter? = null;

        fun initialise(canvas: HTMLCanvasElement, drawBoundingBoxes: Boolean = false) {
            painterInstance = Painter(canvas.getContext("2d") as CanvasRenderingContext2D, canvas, drawBoundingBoxes)
        }

        fun getInstance(): Painter {
            return painterInstance!!
        }
    }
}