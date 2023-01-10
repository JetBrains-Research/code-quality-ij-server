package org.jetbrains.research.ij.headless.server

import io.grpc.Server
import io.grpc.ServerBuilder

class IJCodeServer(private val port: Int) {

    private val server: Server = ServerBuilder.forPort(port).addService(IJCodeInspectionService()).build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                stop()
                println("*** server shut down")
            }
        )
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}
