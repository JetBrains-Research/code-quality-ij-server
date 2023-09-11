package org.jetbrains.research.ij.headless.server

import com.intellij.lang.Language
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.research.ij.headless.server.utils.createProject
import org.jetbrains.research.ij.headless.server.utils.createPsiFile
import org.jetbrains.research.ij.headless.server.utils.updatePsiFileContent
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference


class PsiFileManager(private val templatesDirPath: Path?, private val language: Language) {

    private val logger = LoggerFactory.getLogger(javaClass)

    data class SingleFileProject(
        val language: Language,
        val project: Project,
        val file: PsiFile,
        private val disposable: Disposable
    )

    private val singleFileProject = run {
        logger.info("Start to create a new project for language \"${language.id}\"")

        val (project, disposable) = createProject(
            language,
            templatesDirPath?.resolve(language.id.lowercase()) ?: Path.of(".")
        ) ?: run {
            logger.error("Can not create a project for language \"${language.id}\"")
            error("Can not create a project for language \"${language.id}\"")
        }

        logger.info("Start to create a new file for language \"${language.id}\"")

        val file = AtomicReference<PsiFile>()

        when (language.id) {
            LanguageId.Python.name -> ApplicationManager.getApplication().invokeAndWait {
                val psiFile = createPsiFile(project, "${language.id}_dummy", language, "")
                file.set(psiFile)
            }

            LanguageId.kotlin.name -> ApplicationManager.getApplication().invokeAndWait {
                val scope = GlobalSearchScope.allScope(project)
                val psiManager = PsiManager.getInstance(project)

                val mainVirtualFile = FilenameIndex.getVirtualFilesByName("Main.kt", scope).first()
                file.set(psiManager.findFile(mainVirtualFile))
            }

            else -> {
                logger.error("Can not create a file for language \"${language.id}\"")
                error("Can not create a file for language \"${language.id}\"")
            }
        }

        logger.info("New file for language has been successfully created")
        SingleFileProject(language, project, file.get(), disposable)
    }

    fun getPsiFile(text: String = "") = singleFileProject.file.apply {
        logger.info("Start to update psi file content...")
        ApplicationManager.getApplication().invokeAndWait { updatePsiFileContent(this, text) }
    }
}
