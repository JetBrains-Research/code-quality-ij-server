import com.google.protobuf.gradle.*
import org.gradle.internal.os.OperatingSystem

group = rootProject.group
version = rootProject.version

apply {
    plugin(libs.plugins.protobuf.get().pluginId)
}

dependencies {
    implementation(libs.protobuf.java)
    implementation(libs.grpc.stub)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.netty)
    implementation(libs.grpc.stub.kotlin)
}

val protocPlatform: String? by project
val jdk7Suffix = "jdk7@jar"

fun makeMacOsXDependency(dependency: String, suffix: String = "osx-x86_64"): String {
    return if (OperatingSystem.current().isMacOsX) {
        "$dependency:$suffix"
    } else {
        dependency
    }
}

protobuf {
    protoc {
        artifact = makeMacOsXDependency(libs.protobuf.protoc.get().toString())
    }
    plugins {
        create("grpc") {
            artifact = makeMacOsXDependency(libs.grpc.protoc.java.get().toString())
        }
        create("grpckt") {
            artifact = makeMacOsXDependency(libs.grpc.protoc.kotlin.get().toString(), jdk7Suffix)
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}
