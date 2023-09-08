package org.jetbrains.research.ij.headless.server.inspector

import com.intellij.codeInsight.daemon.impl.DaemonProgressIndicator
import com.intellij.codeInspection.*
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.inspector.configs.BaseIJCodeInspectorConfig
import org.jetbrains.research.ij.headless.server.inspector.configs.python.PythonIJCodeInspectorConfig
import org.slf4j.LoggerFactory

/** @IJCodeInspector provides opportunity to invoke IDE code quality inspections on given file. */
object IJCodeInspector : Inspector {

    private val logger = LoggerFactory.getLogger(javaClass)
    private fun getIJCodeInspectorConfig(language: Language) = when (language.id) {
        "Python" -> PythonIJCodeInspectorConfig
        else -> object : BaseIJCodeInspectorConfig() {}
    }

    fun getInspections(language: Language, config: BaseIJCodeInspectorConfig) =
        language.getAllInspections().filter { it.id !in config.ignoredInspectionIds }.toList()
            .also { logger.info("Found ${it.size} inspections for language $language") }

    private fun inspectSingle(psiFile: PsiFile, tool: LocalInspectionTool): List<ProblemDescriptor> {
        var problems: List<ProblemDescriptor>? = null
        val inspectionManager = InspectionManager.getInstance(psiFile.project)

        ProgressManager.getInstance().executeProcessUnderProgress(
            { problems = tool.processFile(psiFile, inspectionManager) },
            DaemonProgressIndicator()
        )

        return problems ?: error("Can not get problems")
    }

    fun inspectSingleAndBuildAdaptedInspection(
        psiFile: PsiFile,
        inspection: LocalInspectionTool,
        config: BaseIJCodeInspectorConfig
    ) = inspectSingle(psiFile, inspection).mapNotNull {
        it.buildAdaptedInspection(inspection, config)
    }

    /** Runs language inspections on given code snippet and returns detected problems. */
    override fun inspect(psiFile: PsiFile): List<AdaptedInspection> {
        logger.info("Running code inspections...")
        logger.debug(psiFile.text)
        ApplicationManager.getApplication().assertIsDispatchThread()
        val config = getIJCodeInspectorConfig(psiFile.language)
        return getInspections(psiFile.language, config).map { inspection ->
            inspectSingleAndBuildAdaptedInspection(psiFile, inspection, config)
        }.flatten()
    }

    private fun ProblemDescriptor.buildAdaptedInspection(
        inspection: LocalInspectionTool,
        config: BaseIJCodeInspectorConfig
    ): AdaptedInspection? {
        val messagesToDelete = config.inspectionIdToDisabledMessages[inspection.id]
        messagesToDelete ?: return AdaptedInspection.build(inspection, this, config)
        val message = ProblemDescriptorUtil.renderDescriptionMessage(
            this,
            this.psiElement
        )
        return if (!messagesToDelete.any { it in message }) {
            AdaptedInspection.build(inspection, this, config, message)
        } else {
            null
        }
    }
}

fun Language.getAllInspections() = LocalInspectionEP.LOCAL_INSPECTION.extensions.filter { it.language == this.id }
    .mapNotNull { it.instantiateTool() as? LocalInspectionTool }
