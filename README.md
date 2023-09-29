# IntelliJ Code Server

IntelliJ Code Server is an example project how [IntelliJ platform](https://www.jetbrains.com/opensource/idea/) can be used as a server
to provide some IntelliJ functionality (like code quality analysis) for side services.

## Description

The project consist of several modules:

1) [ij-core](./ij-core) is responsible for opening a project (e.g. Python or Kotlin project)
and working with its [PSI](https://plugins.jetbrains.com/docs/intellij/psi.html). 
Also, it has [IJCodeInspector](./ij-core/src/main/kotlin/org/jetbrains/research/ij/headless/server/inspector/IJCodeInspector.kt) 
that can find [inspections](https://www.jetbrains.com/help/idea/code-inspection.html) by language and perform them on a code fragment.

2) [ij-server](./ij-server) is responsible for listening a port and apply services, 
e.g. [CodeInspectionServiceImpl](./ij-server/src/main/kotlin/org/jetbrains/research/ij/headless/server/CodeInspectionServiceImpl.kt) 
that finds a file and returns a list of inspections that were found.

3) [ij-starter](./ij-starter) is responsible for starting a new instance of the ij-server by a config file.

4) [ij-model](./ij-model) is responsible for storing models for the server, e.g. the server's input and output etc.

## How to run the server?

### Run the server locally

Run `./gradlew :ij-starter:runIde -Pconfig=path/to/config/file` command from the root of the project.

The config file must be a JSON file with several fields (see [CodeServerConfig.kt](./ij-starter/src/main/kotlin/org/jetbrains/research/ij/headless/server/CodeServerConfig.kt) file). 
An example of the config file can be found [here](./ij-starter/src/main/resources/config.json). Possible options for language are listed below:
- `kotlin` for Kotlin
- `Python` for Python

You could also specify several optional arguments:

| Argument                        | Description                                                                                                                                   |
|---------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| **&#8209;&#8209;PloggingLevel** | Logging level. The available options are listed [here](https://logging.apache.org/log4j/2.x/manual/customloglevels.html). By default, `INFO`. |
| **&#8209;&#8209;PlogsPath**     | Path to a file where to save logs. By default, all logs are outputted to stdout.                                                              |

This repository already contains predefined [template projects](./templates) for different language. 
To run inspections correctly, you should additionally set up templates. 
Instructions on how to do it you could find in the [corresponding README file](./templates/README.md).
In this file you could also find notes about requirements for your own custom template projects.

### Run the server in a docker image

**TODO**

## How to run the client?

This repository contains an example of client in Python, you just need to navigate to the [scripts](./scripts) 
folder and follow the [README instructions](./scripts/README.md).