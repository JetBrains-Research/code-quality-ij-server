package org.jetbrains.research.ij.headless.server

import com.intellij.codeInspection.ProblemDescriptorUtil
import com.intellij.lang.Language
import com.intellij.psi.PsiFile
import io.grpc.stub.StreamObserver
import org.jetbrains.research.ij.headless.server.utils.PsiUtils

class IJCodeInspectionService : CodeInspectionServiceGrpc.CodeInspectionServiceImplBase() {

//    private val project = PsiUtils.createProject()
    private val dummyPsiFiles = mutableMapOf<Language, PsiFile>()
    override fun inspect(code: Code, responseObserver: StreamObserver<InspectionResult>) {
//        val languageId = code.languageId.name
//        val language = Language.findLanguageByID(code.languageId.name)
//            ?: error("No such language by id $languageId")
//        val file = getDummyFile(language, code.text)
//        val result = IJCodeInspector.inspect(file)
//
//        val response = InspectionResult.newBuilder().addAllProblems(
//            result.flatMap { (inspection, descriptors) ->
//                descriptors.map { descriptor ->
//                    Problem.newBuilder()
//                        .setName(ProblemDescriptorUtil.renderDescriptionMessage(descriptor, descriptor.psiElement))
//                        .setInspector(inspection.shortName)
//                        .setLineNumber(descriptor.lineNumber.toLong())
//                        .setOffset(descriptor.psiElement?.textOffset?.toLong() ?: -1L)
//                        .setLength(descriptor.psiElement?.textLength?.toLong() ?: -1L)
//                        .build()
//                }
//            }
//        ).build()
//
//        responseObserver.onNext(response)
//        responseObserver.onCompleted()
    }

//    private fun getDummyFile(language: Language, text: String): PsiFile {
//        return dummyPsiFiles.getOrPut(language) {
//            return PsiUtils.createPsiFile(project, "${language.id}_dummy", language, "")
//        }.also { PsiUtils.updatePsiFileContent(it, text) }
//    }
}
