group = rootProject.group
version = rootProject.version

dependencies {
    implementation(rootProject.libs.kotlin.argparser)
    implementation(project(":ij-core"))
}

tasks {
    runIde {
        // Define args for your application
        val message: String? by project

        args = listOfNotNull(
            // Define your application starter command name
            "ij-headless-greeting",
            // Define args for your application
            message?.let { "--message=$it" }
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
