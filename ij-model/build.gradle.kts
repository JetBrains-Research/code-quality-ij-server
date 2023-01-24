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

fun makeMacOsXDependency(dependency: String): String {
    return if (OperatingSystem.current().isMacOsX) {
        "$dependency:osx-x86_64"
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
            artifact = libs.grpc.protoc.kotlin.get().toString() + ":jdk7@jar"
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
