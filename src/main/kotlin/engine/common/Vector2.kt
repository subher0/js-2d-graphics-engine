package engine.common

class Vector2(private var x: Double, private var y: Double) {
    fun getX(): Double = x
    fun getY(): Double = y

    fun plus(vector: Vector2) {
        x += vector.x
        y += vector.y
    }

    fun minus(vector: Vector2) {
        x -= vector.x
        y -= vector.y
    }

    fun multiply(vector: Vector2) {
        x *= vector.x
        y *= vector.y
    }

    fun divide(vector: Vector2) {
        x /= vector.x
        y /= vector.y
    }

    fun scale(factor: Double) {
        x *= factor
        y *= factor
    }

    fun copy(): Vector2 {
        return Vector2(x, y)
    }

    companion object {
        fun zero() = Vector2(0.0, 0.0)
    }
}