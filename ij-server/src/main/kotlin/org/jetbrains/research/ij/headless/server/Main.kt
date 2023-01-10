package org.jetbrains.research.ij.headless.server

fun main() {
    val server = IJCodeServer(8080)
        server.start()
        server.blockUntilShutdown()
}
