package org.jetbrains.research.ij.headless

import com.intellij.openapi.application.ApplicationStarter
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class IJGreetingApplicationStarter : ApplicationStarter {

    override fun getCommandName(): String {
        return "ij-headless-greeting"
    }

    override fun main(args: List<String>) {
        val parser = ArgParser(args.drop(1).toTypedArray())

        val greeting by parser.storing(
            "-m",
            "--message",
            help = "IJ Headless greeting message"
        ).default("Hello from IJ Headless")

        println(greeting)
    }
}
