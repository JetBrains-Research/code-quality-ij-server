package org.jetbrains.research.ij.headless.server

import io.grpc.Server
import io.grpc.ServerBuilder
import org.slf4j.LoggerFactory
import java.nio.file.Path

class CodeServerImpl(private val port: Int, languages: List<String>, templatesDirPath: Path) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val psiFileManager = PsiFileManager(templatesDirPath).also { manager ->
        languages.forEach { manager.initSingleFileProject(it) }
    }

    private val server: Server =
        ServerBuilder.forPort(port).addService(CodeInspectionServiceImpl(psiFileManager)).build()

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

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}
