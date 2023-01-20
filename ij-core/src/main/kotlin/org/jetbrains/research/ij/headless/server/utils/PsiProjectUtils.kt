package org.jetbrains.research.ij.headless.server.utils

import com.intellij.lang.Language
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.jetbrains.python.PythonLanguage
import org.jetbrains.research.pluginUtilities.openProject.getPythonProjectOpener
import java.nio.file.Path

fun createPythonProject(projectPath: Path): Project? {
    val projectOpener = getPythonProjectOpener()
    val disposable = Disposer.newDisposable()

    return projectOpener.open(projectPath, disposable, true)
}

fun createProject(language: Language, templatesDir: Path = Path.of(".")): Project? {
    if (language == PythonLanguage.INSTANCE) {
        return createPythonProject(templatesDir.resolve("python"))
    }
    error("Language $language is not supported.")
}
