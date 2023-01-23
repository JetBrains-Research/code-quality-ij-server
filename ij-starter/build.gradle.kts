group = rootProject.group
version = rootProject.version

dependencies {
    implementation(rootProject.libs.kotlinx.serialization.json)
    implementation(rootProject.libs.kotlin.argparser)
    implementation(project(":ij-server"))
}

apply {
    plugin(libs.plugins.kotlin.serialization.get().pluginId)
}

tasks {
    runIde {
        // Server config
        val config: String? by project

        args = listOfNotNull(
            // Define your application starter command name
            "ij-code-server",
            // Define args for your application
            config?.let { "--config=$it" }
        )

        jvmArgs = listOf(
            "-Djava.awt.headless=true",
            "--add-exports",
            "java.base/jdk.internal.vm=ALL-UNNAMED",
            "-Djdk.module.illegalAccess.silent=true"
        )

        maxHeapSize = "32g"
    }
}
