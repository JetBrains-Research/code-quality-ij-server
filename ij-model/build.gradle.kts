group = rootProject.group
version = rootProject.version

apply {
    plugin(libs.plugins.protobuf.get().pluginId)
}

protobuf {
    protoc {artifact = "com.google.protobuf:protoc:3.13.0"}
    plugins{
        grpc {artifact = "io.grpc:protoc-gen-grpc-java:1.31.1"}
    }
    generateProtoTasks{
        all()*.plugins {grpc {}}
    }
    // default proto plugin generate stub in build folder
    // change the stub generate folder
    //generatedFilesBaseDir = "$projectDir/src/generated"
}

sourceSets{
    main{
        proto{
            srcDirs 'src/main/proto'
        }
        java{
            srcDirs 'build/generated/source/proto/main/grpc'
            srcDirs 'build/generated/source/proto/main/kotlin'

        }
    }
}