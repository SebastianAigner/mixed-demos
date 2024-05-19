package io.sebi.demos

import kotlinx.coroutines.delay

object Repository {
    suspend fun save(s: String) {
        delay(500)
        println("Saved $s to database!")
    }
}
