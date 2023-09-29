package org.jetbrains.research.ij.headless.server.inspector

import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.inspector.configs.python.PythonPep8AnnotatorConfig
import org.slf4j.LoggerFactory

object AnnotatorInspector : Inspector {
    private val logger = LoggerFactory.getLogger(javaClass)
    private fun getAnnotatorConfigs(language: Language) = when (language.id) {
        "Python" -> listOf(PythonPep8AnnotatorConfig)
        else -> emptyList()
    }

    override fun inspect(psiFile: PsiFile): List<AdaptedInspection> {
        logger.info("Running code inspections...")
        logger.debug(psiFile.text)
        ApplicationManager.getApplication().assertIsDispatchThread()
        return getAnnotatorConfigs(psiFile.language).map { config ->
            val information = config.annotator.collectInformation(psiFile)
            information?.let {
                config.annotator.doAnnotate(information)?.problems?.map {
                    AdaptedInspection(it.code, it.description, it.line.toLong(), it.column.toLong())
                }
            }?.filter { it.inspector !in config.ignoredInspectionIds } ?: emptyList()
        }.flatten()
    }
}
