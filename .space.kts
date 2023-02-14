job("Publish to Docker Hub") {
    startOn {
        gitPush {
            branchFilter {
                +"refs/heads/master"
            }
        }
    }

    host("Build artifacts and a Docker image") {
        env["DOCKER_USER"] = Secrets("DOCKER_USER")
        env["DOCKER_PASSWORD"] = Secrets("DOCKER_PASSWORD")

        shellScript {
            content = """
                docker login registry.jetbrains.team --username ${'$'}DOCKER_USER --password "${'$'}DOCKER_PASSWORD"
            """
        }

        dockerBuildPush {
            val spaceRepo = "registry.jetbrains.team/p/code-quality-for-online-learning-platforms/containers/code-quality-ij-server"
            tags {
                +"$spaceRepo:1.0.${"$"}JB_SPACE_EXECUTION_NUMBER"
            }
        }
    }
}
