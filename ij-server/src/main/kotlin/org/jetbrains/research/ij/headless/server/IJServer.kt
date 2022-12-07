package org.jetbrains.research.ij.headless.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket

class IJServer(serverPort: Int, private val serverHost: String) {

    private val requestProcessor = CodeServerRequestProcessor()

    private val serverSocket = ServerSocket(serverPort)

    fun run() {
        runBlocking {
            launch(Dispatchers.IO) {
                while (true) {
                    val socket = serverSocket.accept()
                    requestProcessor.accept(socket)
                }
            }
        }
    }
}
