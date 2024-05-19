package io.sebi.demos

import kotlinx.coroutines.delay

suspend fun saveToDatabase(s: String) {
    delay(500)
    println("Saved $s to database!")
}