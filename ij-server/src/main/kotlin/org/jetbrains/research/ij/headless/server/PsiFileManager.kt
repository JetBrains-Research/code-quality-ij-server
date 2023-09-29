package org.jetbrains.research.ij.headless.server

import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import org.jetbrains.research.ij.headless.server.utils.createProject
import org.jetbrains.research.ij.headless.server.utils.updatePsiFileContent
import org.slf4j.LoggerFactory
import java.nio.file.Path


class PsiFileManager(private val templatesDirPath: Path?, private val language: Language) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val singleFileProject = run {
        logger.info("Start to create a new project for language \"${language.id}\"")

        val (project, disposable) = createProject(
            language,
            templatesDirPath?.resolve(language.id.lowercase()) ?: Path.of(".")
        ) ?: run {
            logger.error("Can not create a project for language \"${language.id}\"")
            error("Can not create a project for language \"${language.id}\"")
        }

        SingleFileProjectConfigurator.configureProject(project, language, disposable)
    }

    fun getPsiFile(text: String = "") = singleFileProject.file.apply {
        logger.info("Start to update psi file content...")
        ApplicationManager.getApplication().invokeAndWait { updatePsiFileContent(this, text) }
    }
}
