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
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString() // + ":osx-x86_64"
    }
    plugins {
        id("grpc") {
            artifact = libs.grpc.protoc.get().toString() // + ":osx-x86_64"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") { }
            }
        }
    }
}
