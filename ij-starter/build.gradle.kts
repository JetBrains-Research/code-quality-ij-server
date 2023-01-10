group = rootProject.group
version = rootProject.version

dependencies {
    implementation(rootProject.libs.kotlin.argparser)
    implementation(project(":ij-server"))
}

tasks {
    runIde {
        // Server port
        val port: Int? by project

        args = listOfNotNull(
            // Define your application starter command name
            "ij-code-server",
            // Define args for your application
            port?.let { "--port=$it" },
        )

        jvmArgs = listOf(
            "-Djava.awt.headless=true",
            "--add-exports",
            "java.base/jdk.internal.vm=ALL-UNNAMED",
            "-Djdk.module.illegalAccess.silent=true"
        )

        maxHeapSize = "32g"

        standardInput = System.`in`
        standardOutput = System.`out`
    }
}
