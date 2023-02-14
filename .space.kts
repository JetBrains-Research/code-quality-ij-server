job("Publish to Docker Hub") {
    host("Build artifacts and a Docker image") {
        env["HUB_USER"] = Secrets("DOCKER_USER")
        env["HUB_TOKEN"] = Secrets("DOCKER_PASSWORD")

        shellScript {
            content = """
                docker login --username ${'$'}HUB_USER --password "${'$'}HUB_TOKEN"
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