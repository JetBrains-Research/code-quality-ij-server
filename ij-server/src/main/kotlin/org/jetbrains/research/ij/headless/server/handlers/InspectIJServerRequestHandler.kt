package org.jetbrains.research.ij.headless.server.handlers

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemDescriptorUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.InspectionResult
import org.jetbrains.research.ij.headless.server.InspectionService
import org.jetbrains.research.ij.headless.server.Problem

class InspectIJServerRequestHandler :
    IJServerRequestHandler<Map<LocalInspectionTool, List<ProblemDescriptor>>, InspectionResult> {

    private val inspectionService = InspectionService()

    override fun handle(psiFile: PsiFile): Map<LocalInspectionTool, List<ProblemDescriptor>> {
        ApplicationManager.getApplication().assertIsDispatchThread()

        return inspectionService.inspect(psiFile)
    }

    override fun toResponse(result: Map<LocalInspectionTool, List<ProblemDescriptor>>): InspectionResult {
        return InspectionResult(
            result.flatMap { (inspection, descriptors) ->
                descriptors.map { descriptor ->
                    Problem(
                        ProblemDescriptorUtil.renderDescriptionMessage(descriptor, descriptor.psiElement),
                        inspection.shortName,
                        descriptor.lineNumber,
                        descriptor.psiElement?.textOffset,
                        descriptor.psiElement?.textLength
                    )
                }
            }
        )
    }
}
