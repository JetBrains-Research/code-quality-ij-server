job("Publish to Docker Hub") {
    host("Build artifacts and a Docker image") {
        env["DOCKER_USER"] = Secrets("DOCKER_USER")
        env["DOCKER_PASSWORD"] = Secrets("DOCKER_PASSWORD")

        shellScript {
            content = """
                docker login registry.jetbrains.team --username ${'$'}DOCKER_USER --password "${'$'}DOCKER_PASSWORD"
            """
        }

        dockerBuildPush {
            labels["vendor"] = "jetbrains-research"
            tags {
                +"jetbrains-research/code-quality-ij-server:1.0.${"$"}JB_SPACE_EXECUTION_NUMBER"
            }
        }
    }
}