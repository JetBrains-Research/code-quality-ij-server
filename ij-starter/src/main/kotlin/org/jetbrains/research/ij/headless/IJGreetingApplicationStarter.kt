package org.jetbrains.research.ij.headless

import com.intellij.openapi.application.ApplicationStarter
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.util.logging.Logger

class IJGreetingApplicationStarter : ApplicationStarter {

    private val log = Logger.getLogger(javaClass.name)
    override fun getCommandName(): String {
        return "ij-headless-greeting"
    }

    /** Sets main (start) thread for IDE in headless as not edt. */
    override fun getRequiredModality(): Int {
        return ApplicationStarter.NOT_IN_EDT
    }

    override fun main(args: List<String>) {
        val parser = ArgParser(args.drop(1).toTypedArray())

        val greeting by parser.storing(
            "-m",
            "--message",
            help = "IJ Headless greeting message"
        ).default("Hello from IJ Headless")

        val analyzer = PythonGreetingCodeAnalyzer()
        log.info(greeting)

        analyzer.run(greeting)
    }
}
