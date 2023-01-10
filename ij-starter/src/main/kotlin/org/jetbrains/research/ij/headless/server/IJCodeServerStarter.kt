package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.application.ApplicationStarter
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.util.logging.Logger

class IJCodeServerStarter : ApplicationStarter {

    private val log = Logger.getLogger(javaClass.name)
    override fun getCommandName(): String {
        return "ij-code-server"
    }

    /** Sets main (start) thread for IDE in headless as not edt. */
    override fun getRequiredModality(): Int {
        return ApplicationStarter.NOT_IN_EDT
    }

    override fun main(args: List<String>) {
        val parser = ArgParser(args.drop(1).toTypedArray())

        val port by parser.storing(
            "--port",
            help = "IJ Code Server port"
        ) { toInt() }.default(8080)

        log.info("Starting IJ Code Server on port=$port")
        val server = IJCodeServer(port)
//        server.start()
//        server.blockUntilShutdown()
    }
}
