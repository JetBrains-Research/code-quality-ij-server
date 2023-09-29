package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.application.ApplicationStarter
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Path
import kotlin.system.exitProcess


class CodeServerStarter : ApplicationStarter {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Deprecated("Specify it as `id` for extension definition in a plugin descriptor")
    override val commandName: String = "ij-code-server"

    /** Sets main (start) thread for IDE in headless as not edt. */
    override val requiredModality: Int = ApplicationStarter.NOT_IN_EDT

    @Suppress("TooGenericExceptionCaught")
    @OptIn(ExperimentalSerializationApi::class)
    override fun main(args: List<String>) {
        val parser = ArgParser(args.drop(1).toTypedArray())

        val configPath by parser.storing(
            "--config",
            help = "IJ Code Server config path"
        )

        val loggingLevel by parser.storing(
            "--loggingLevel",
            help = "Logging level",
        ) { Level.toLevel(this, Level.INFO) }.default(Level.INFO)

        val logsPath by parser.storing(
            "--logsPath",
            help = "Path to a file where to store logs",
        ) { Path.of(this).toAbsolutePath() }.default(null)

        configureLogger(loggingLevel, logsPath)

        logger.info("Parsing IJ Server config from file $configPath")

        val config = try {
            // For some reason if this line throws an exception it would be swallowed by ApplicationStarter ...
            Json.decodeFromStream<CodeServerConfig>(File(configPath).inputStream())
        } catch (e: Exception) {
            // ... so we catch all exceptions, log them and exit.
            logger.error(e.message)
            exitProcess(1)
        }

        logger.info("IJ Server config data $config")

        logger.info("Starting IJ Code Server on port=${config.port}")
        val server = CodeServerImpl(config.port, config.language, Path.of(config.templatesPath).toAbsolutePath())
        server.start()
        server.blockUntilShutdown()
    }

    private fun configureLogger(loggingLevel: Level, logsPath: Path?) {
        val builder = ConfigurationBuilderFactory.newConfigurationBuilder()

        val patternLayout = builder.newLayout("PatternLayout")
        patternLayout.addAttribute("pattern", "%d | %-5p | [%c] %m%n")

        val stdoutAppender = builder.newAppender("stdout", "Console")
        stdoutAppender.add(patternLayout)

        val fileAppender = logsPath?.let {
            val fileAppender = builder.newAppender("file", "File")
            fileAppender.addAttribute("fileName", it)
            fileAppender.add(patternLayout)
        }

        val rootLogger = builder.newRootLogger(loggingLevel)
        rootLogger.add(builder.newAppenderRef(stdoutAppender.name))
        fileAppender?.let { rootLogger.add(builder.newAppenderRef(it.name)) }

        builder.add(rootLogger)
        builder.add(stdoutAppender)
        fileAppender?.let { builder.add(it) }

        Configurator.reconfigure(builder.build())
    }
}
