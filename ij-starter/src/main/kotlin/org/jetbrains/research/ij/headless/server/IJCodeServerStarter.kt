package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.application.ApplicationStarter
import com.intellij.openapi.diagnostic.Logger
import com.xenomachina.argparser.ArgParser
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.nio.file.Paths

class IJCodeServerStarter : ApplicationStarter {

    private val logger = Logger.getInstance(javaClass)

    @Deprecated("Specify it as `id` for extension definition in a plugin descriptor")
    override val commandName: String = "ij-code-server"

    /** Sets main (start) thread for IDE in headless as not edt. */
    override val requiredModality: Int = ApplicationStarter.NOT_IN_EDT

    @OptIn(ExperimentalSerializationApi::class)
    override fun main(args: List<String>) {
        val parser = ArgParser(args.drop(1).toTypedArray())

        val configPath by parser.storing(
            "--config",
            help = "IJ Code Server config path"
        )

        println(configPath)
        val config = Json.decodeFromStream<IJCodeServerConfig>(File(configPath).inputStream())
        println(config)

        logger.info("Starting IJ Code Server on port=${config.port}")

        val server = IJCodeServer(config.port, Paths.get(config.templatesPath))
        server.start()
        server.blockUntilShutdown()
    }
}
