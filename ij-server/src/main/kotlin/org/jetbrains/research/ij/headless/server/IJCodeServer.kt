package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.diagnostic.Logger
import io.grpc.Server
import io.grpc.ServerBuilder

class IJCodeServer(private val port: Int) {

    private val logger = Logger.getInstance(javaClass)

    private val server: Server = ServerBuilder.forPort(port).addService(IJCodeInspectionService()).build()

    fun start() {
        server.start()
        logger.info("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                logger.info("Shutting down gRPC server since JVM is shutting down")
                stop()
                logger.info("Server shut down")
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
