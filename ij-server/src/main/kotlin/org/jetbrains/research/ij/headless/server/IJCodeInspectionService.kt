package org.jetbrains.research.ij.headless.server

import com.intellij.codeInspection.ProblemDescriptorUtil
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.utils.PsiUtils
import java.util.concurrent.atomic.AtomicReference

class IJCodeInspectionService : CodeInspectionServiceGrpcKt.CodeInspectionServiceCoroutineImplBase() {

    private val project = PsiUtils.createProject()
    private val dummyPsiFiles = mutableMapOf<Language, PsiFile>()

    override suspend fun inspect(request: Code): InspectionResult {
        val languageId = request.languageId.name
        val language = Language.findLanguageByID(request.languageId.name)
            ?: error("No such language by id $languageId")
        val file = getDummyFile(language, request.text)
        val result = IJCodeInspector.inspect(file)

        val response = AtomicReference<InspectionResult>()
        ApplicationManager.getApplication().runReadAction {

        response.set(InspectionResult.newBuilder().addAllProblems(
                result.flatMap { (inspection, descriptors) ->
                    descriptors.map { descriptor ->
                        Problem.newBuilder()
                            .setName(ProblemDescriptorUtil.renderDescriptionMessage(descriptor, descriptor.psiElement))
                            .setInspector(inspection.shortName)
                            .setLineNumber(descriptor.lineNumber.toLong())
                            .setOffset(descriptor.psiElement?.textOffset?.toLong() ?: -1L)
                            .setLength(descriptor.psiElement?.textLength?.toLong() ?: -1L)
                            .build()
                    }
                }
            ).build())
        }

        return response.get()
    }

    private fun getDummyFile(language: Language, text: String): PsiFile {
        return dummyPsiFiles.getOrPut(language) {
            return PsiUtils.createPsiFile(project, "${language.id}_dummy", language, "")
        }.also { PsiUtils.updatePsiFileContent(it, text) }
    }
}
