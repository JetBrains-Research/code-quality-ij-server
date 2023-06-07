package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.application.ApplicationStarter
import com.xenomachina.argparser.ArgParser
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Path

class CodeServerStarter : ApplicationStarter {

    private val logger = LoggerFactory.getLogger(javaClass)

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

        logger.info("Parsing IJ Server config from file $configPath")
        val config = Json.decodeFromStream<CodeServerConfig>(File(configPath).inputStream())
        logger.info("IJ Server config data $config")

        logger.info("Starting IJ Code Server on port=${config.port}")
        val server = CodeServerImpl(config.port, config.languages, Path.of(config.templatesPath).toAbsolutePath())
        server.start()
        server.blockUntilShutdown()
    }
}
