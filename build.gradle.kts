group = "org.jetbrains.research.ij.headless.server"
version = "1.0-SNAPSHOT"

@Suppress("DSL_SCOPE_VIOLATION") // "libs" produces a false-positive warning, see https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.jetbrains.intellij)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.protobuf) apply false
}

val platformVersion: String by project
val platformType: String by project
val platformDownloadSources: String by project
val platformPlugins: String by project

allprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
        plugin(rootProject.libs.plugins.dokka.get().pluginId)
        plugin(rootProject.libs.plugins.gradle.ktlint.get().pluginId)
        plugin(rootProject.libs.plugins.jetbrains.intellij.get().pluginId)
    }

    dependencies {
        implementation(rootProject.libs.log4j.core)
        implementation(rootProject.libs.log4j.api)
        implementation(rootProject.libs.log4j.slf4j.impl)
    }

    repositories {
        mavenCentral()
    }

    intellij {
        version.set(platformVersion)
        type.set(platformType)
        downloadSources.set(platformDownloadSources.toBoolean())
        updateSinceUntilBuild.set(true)
        plugins.set(platformPlugins.split(',').map(String::trim).filter(String::isNotEmpty))
    }

    ktlint {
        disabledRules.set(setOf("no-wildcard-imports"))
        enableExperimentalRules.set(true)
        filter {
            exclude("**/resources/**")
        }
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "11"
            targetCompatibility = "11"
        }
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
            kotlinOptions.jvmTarget = "11"
        }
        withType<org.jetbrains.intellij.tasks.BuildSearchableOptionsTask>()
            .forEach { it.enabled = false }
    }
}
