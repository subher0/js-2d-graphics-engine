package engine.common

fun Float.format(digits: Int): String = this.asDynamic().toFixed(digits)