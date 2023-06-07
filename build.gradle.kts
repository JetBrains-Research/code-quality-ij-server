group = "org.jetbrains.research.ij.headless.server"
version = "1.0-SNAPSHOT"

@Suppress("DSL_SCOPE_VIOLATION") // "libs" produces a false-positive warning, see https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.jetbrains.intellij)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.detekt) apply false
}

val platformVersion: String by project
val platformType: String by project
val platformDownloadSources: String by project
val platformPlugins: String by project
val detektReportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/merge.sarif"))
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
        plugin(rootProject.libs.plugins.dokka.get().pluginId)
        plugin(rootProject.libs.plugins.jetbrains.intellij.get().pluginId)
    }

    dependencies {
        implementation(rootProject.libs.log4j.core)
        implementation(rootProject.libs.log4j.api)
        implementation(rootProject.libs.log4j.slf4j.impl)
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://packages.jetbrains.team/maven/p/big-code/bigcode")
    }

    intellij {
        version.set(platformVersion)
        type.set(platformType)
        downloadSources.set(platformDownloadSources.toBoolean())
        updateSinceUntilBuild.set(true)
        plugins.set(platformPlugins.split(',').map(String::trim).filter(String::isNotEmpty))
    }

    apply<io.gitlab.arturbosch.detekt.DetektPlugin>()

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        config = rootProject.files("detekt.yml")
        buildUponDefaultConfig = true
        debug = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
        finalizedBy(detektReportMerge)
        reports.sarif.required.set(true)
        detektReportMerge.get().input.from(sarifReportFile)
    }

    tasks.getByPath("detekt").onlyIf { project.hasProperty("runDetekt") }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "17"
            targetCompatibility = "17"
        }
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
            kotlinOptions.jvmTarget = "17"
        }
        withType<org.jetbrains.intellij.tasks.BuildSearchableOptionsTask>().forEach { it.enabled = false }
    }
}
