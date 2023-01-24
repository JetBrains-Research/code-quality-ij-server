package org.jetbrains.research.ij.headless.server

import com.intellij.lang.Language
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.utils.createProject
import org.jetbrains.research.ij.headless.server.utils.createPsiFile
import org.jetbrains.research.ij.headless.server.utils.updatePsiFileContent
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference

class PsiFileManager(private val templatesPath: Path) {

    private val logger = Logger.getInstance(javaClass)

    data class SingleFileProject(
        val language: Language,
        val project: Project,
        val file: PsiFile,
        private val disposable: Disposable
    )

    private val singleFileProjects = mutableMapOf<Language, SingleFileProject>()

    fun getPsiFile(language: Language, text: String = ""): PsiFile {
        return singleFileProjects.getOrPut(language) {
            logger.info("Start to create new psi project for language $language")

            val (project, disposable) = createProject(
                language,
                templatesPath.resolve(language.id)
            ) ?: error("Can not create project for language $language")

            logger.info("Start to create new for language $language")

            val file = AtomicReference<PsiFile>()
            ApplicationManager.getApplication().invokeAndWait {
                val psiFile = createPsiFile(project, "${language.id}_dummy", language, "")
                file.set(psiFile)
            }

            logger.info("New file for language has been successfully created")
            SingleFileProject(language, project, file.get(), disposable)
        }.file.apply {
            logger.info("Start to update psi file content...")

            ApplicationManager.getApplication().invokeAndWait {
                updatePsiFileContent(this, text)
            }
        }
    }
}
