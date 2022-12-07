group = "org.jetbrains.research.ij.headless"
version = "1.0-SNAPSHOT"

apply {
    plugin(libs.plugins.kotlin.serialization.get().pluginId)
}

dependencies {
    implementation(rootProject.libs.kotlin.argparser)

    implementation(project(":ij-model"))
    implementation(project(":ij-core"))
}
