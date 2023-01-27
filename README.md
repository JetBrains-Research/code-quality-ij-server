# IntelliJ Code Server

IntelliJ Code Server is an example project how [IntelliJ platform](https://www.jetbrains.com/opensource/idea/) can be used as a server
to provide some IntelliJ functionality (like code quality analysis) for side services.

## Description

The project consist of several modules:

1) [ij-core](./ij-core) is responsible for opening a project (e.g. Python or Kotlin project)
and working with its [PSI](https://plugins.jetbrains.com/docs/intellij/psi.html). 
Also, it has [IJCodeInspector](./ij-core/src/main/kotlin/org/jetbrains/research/ij/headless/server/IJCodeInspector.kt) 
that can find [inspections](https://www.jetbrains.com/help/idea/code-inspection.html) by language and perform them on a code fragment.

2) [ij-server](./ij-server) is responsible for listening a port and apply services, 
e.g. [CodeInspectionServiceImpl](./ij-server/src/main/kotlin/org/jetbrains/research/ij/headless/server/CodeInspectionServiceImpl.kt) 
that finds a file and returns a list of inspections that were found.

3) [ij-starter](./ij-starter) is responsible for starting a new instance of the ij-server by a config file.

4) [ij-model](./ij-model) is responsible for storing models for the server, e.g. the server's input and output etc.

## How to run the server?

### Run the server locally

Run `./gradlew :ij-starter:runIde -Pconfig=path/to/config/file` command from the root of the project.

The config file must be a JSON file with two fields (see [CodeServerConfig.kt](./ij-starter/src/main/kotlin/org/jetbrains/research/ij/headless/server/CodeServerConfig.kt) file). 
An example of the config file:

```json
{
  "port": 8080,
  "templatesPath": "path/to/templates/projects"
}
```

This repository already contains a [template project](./templates) for Python.
To run inspections correctly, firstly, you need to create a venv inside it:
1. Go to the `./templates/Python` folder:
```commandline
cd ./templates/Python/venv
```
2. Create a new virtual environment:
```commandline
python -m virtualenv venv
```
3. Activate the Python venv
```commandline
source venv/bin/activate
```
4. Install the requirements
```commandline
pip install -r requirements.txt
```
This folder can contain several template projects for different languages.
The folder must be called as a language id. The supported languages are listed below:
- Kotlin, id is `kotlin`
- Python, id is `python`

Next you can run the server.


### Run the server in a docker image

**TODO**

## How to run the client?

This repository contains an example of client in Python, you just need to navigate to the [scripts](./scripts) 
folder and follow the README.md instructions.