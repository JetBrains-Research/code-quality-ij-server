package org.jetbrains.research.ij.headless.server.utils

import com.intellij.lang.Language
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.jetbrains.python.PythonLanguage
import org.jetbrains.research.pluginUtilities.openProject.getPythonProjectOpener
import java.nio.file.Path

fun createPythonProject(projectPath: Path): Pair<Project, Disposable>? {
    val projectOpener = getPythonProjectOpener()
    val disposable = Disposer.newDisposable()

    return projectOpener.open(projectPath, disposable, true)?.let { it to disposable }
}

fun createProject(language: Language, projectTemplatesDir: Path = Path.of(".")): Pair<Project, Disposable>? {
    if (language == PythonLanguage.INSTANCE) {
        return createPythonProject(projectTemplatesDir)
    }
    error("Language $language is not supported.")
}
