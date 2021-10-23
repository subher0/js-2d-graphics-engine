package engine.common

import kotlinx.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun delay(ms: Int): Unit = suspendCoroutine { continuation ->
    window.setTimeout({
        println("timeout")
        continuation.resume(Unit)
    }, ms)
}