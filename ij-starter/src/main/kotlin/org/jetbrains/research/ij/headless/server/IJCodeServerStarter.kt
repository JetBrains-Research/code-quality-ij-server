package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.application.ApplicationStarter
import com.intellij.openapi.diagnostic.Logger
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class IJCodeServerStarter : ApplicationStarter {

    private val logger = Logger.getInstance(javaClass)

    @Deprecated("Specify it as `id` for extension definition in a plugin descriptor")
    override val commandName: String = "ij-code-server"

    /** Sets main (start) thread for IDE in headless as not edt. */
    override val requiredModality: Int = ApplicationStarter.NOT_IN_EDT

    override fun main(args: List<String>) {
        val parser = ArgParser(args.drop(1).toTypedArray())

        val port by parser.storing(
            "--port",
            help = "IJ Code Server port"
        ) { toInt() }.default(1234)

        logger.info("Starting IJ Code Server on port=$port")
        val server = IJCodeServer(port)
        server.start()
        server.blockUntilShutdown()
    }
}
