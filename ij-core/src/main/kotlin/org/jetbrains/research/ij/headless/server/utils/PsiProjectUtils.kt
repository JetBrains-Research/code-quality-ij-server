package org.jetbrains.research.ij.headless.server.utils

import com.intellij.lang.Language
import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.jetbrains.python.PythonLanguage
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.research.pluginUtilities.openProject.ProjectOpener
import org.jetbrains.research.pluginUtilities.openProject.getKotlinJavaProjectOpener
import org.jetbrains.research.pluginUtilities.openProject.getPythonProjectOpener
import java.nio.file.Path

fun getProjectOpener(language: Language): ProjectOpener {
    return when (language) {
        PythonLanguage.INSTANCE -> getPythonProjectOpener()
        KotlinLanguage.INSTANCE, JavaLanguage.INSTANCE -> getKotlinJavaProjectOpener()
        else -> error("Language $language is not supported.")
    }
}

fun createProject(language: Language, projectTemplatesDir: Path = Path.of(".")): Pair<Project, Disposable>? {
    val projectOpener = getProjectOpener(language)
    val disposable = Disposer.newDisposable()

    return projectOpener.open(projectTemplatesDir, disposable, false)?.let { it to disposable }
}
