package org.jetbrains.research.ij.headless.server

import com.intellij.codeInsight.daemon.impl.DaemonProgressIndicator
import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalInspectionEP
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiFile

/** @IJCodeInspector provides opportunity to invoke IDE code quality inspections on given file. */
object IJCodeInspector {

    private val logger = Logger.getInstance(javaClass)

    private fun getInspections(language: Language) =
        LocalInspectionEP.LOCAL_INSPECTION.extensions.filter { it.language == language.id }
            .mapNotNull { it.instantiateTool() as? LocalInspectionTool }.toList()

    private fun inspectSingle(psiFile: PsiFile, tool: LocalInspectionTool): List<ProblemDescriptor> {
        var problems: List<ProblemDescriptor>? = null
        val inspectionManager = InspectionManager.getInstance(psiFile.project)

        ProgressManager.getInstance().executeProcessUnderProgress(
            { problems = tool.processFile(psiFile, inspectionManager) },
            DaemonProgressIndicator()
        )

        return problems ?: error("Can not get problems")
    }

    /** Runs language inspections on given code snippet and returns detected problems. */
    fun inspect(psiFile: PsiFile): Map<LocalInspectionTool, List<ProblemDescriptor>> {
        logger.info("Running code inspections...")
        ApplicationManager.getApplication().assertIsDispatchThread()

        return getInspections(psiFile.language).associateWith { inspectSingle(psiFile, it) }
    }
}
