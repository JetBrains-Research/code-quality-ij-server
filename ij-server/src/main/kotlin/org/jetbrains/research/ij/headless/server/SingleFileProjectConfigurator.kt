package org.jetbrains.research.ij.headless.server

import com.intellij.lang.Language
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.utils.findPsiFileByName
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicReference

object SingleFileProjectConfigurator {
    private val logger = LoggerFactory.getLogger(javaClass)

    data class SingleFileProject(
        val language: Language,
        val project: Project,
        val file: PsiFile,
        private val disposable: Disposable
    )

    fun configureProject(project: Project, language: Language, disposable: Disposable): SingleFileProject {
        logger.info("Start to create a new file for language \"${language.id}\"")

        val file = AtomicReference<PsiFile>()

        ApplicationManager.getApplication().invokeAndWait {
            val mainPsiFile = MainFile.fromLanguage(language)?.let { findPsiFileByName(project, it.fileName) } ?: run {
                logger.error("Can not create a file for language \"${language.id}\"")
                error("Can not create a file for language \"${language.id}\"")
            }

            file.set(mainPsiFile)
        }

        logger.info("New file for language has been successfully created")
        return SingleFileProject(language, project, file.get(), disposable)
    }

    enum class MainFile(val languageId: String, val fileName: String) {
        KotlinMainFile(LanguageId.kotlin.name, "Main.kt"),
        PythonMainFile(LanguageId.Python.name, "main.py");

        companion object {
            fun fromLanguage(language: Language) = MainFile.values().associateBy(MainFile::languageId)[language.id]
        }
    }
}
