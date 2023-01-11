import com.google.protobuf.gradle.*

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


protobuf {
    protoc {
        // for apple m1, please add protoc_platform=osx-x86_64 in $HOME/.gradle/gradle.properties
        artifact = libs.protobuf.protoc.get().toString() + (protocPlatform?.let { ":$it" } ?: "")
    }
    plugins {
        create("grpc") {
            artifact = libs.grpc.protoc.java.get().toString() + (protocPlatform?.let { ":$it" } ?: "")
        }
        create("grpckt") {
            artifact = libs.grpc.protoc.kotlin.get().toString() + ":jdk8@jar"
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
