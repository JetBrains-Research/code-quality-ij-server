package org.jetbrains.research.ij.headless.server

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.research.ij.headless.server.inspector.AnnotatorInspector
import org.jetbrains.research.ij.headless.server.inspector.IJCodeInspector
import java.util.concurrent.atomic.AtomicReference

class CodeInspectionServiceImpl(private val psiFileManager: PsiFileManager) :
    CodeInspectionServiceGrpcKt.CodeInspectionServiceCoroutineImplBase() {

    private val logger = Logger.getInstance(javaClass)

    override suspend fun inspect(request: Code): InspectionResult {
        logger.info("Receive request: $request")

        val language = psiFileManager.getLanguageById(request.languageId.name)
        val file = psiFileManager.getPsiFile(language, request.text)

        val response = AtomicReference<InspectionResult>()

        ApplicationManager.getApplication().invokeAndWait {
            val inspections = IJCodeInspector.inspect(file) + AnnotatorInspector.inspect(file)
            response.set(
                InspectionResult.newBuilder().addAllProblems(
                    inspections.map { inspection ->
                        Problem.newBuilder()
                            .setName(inspection.description)
                            .setInspector(inspection.inspector)
                            .setLineNumber(inspection.lineNumber)
                            .setOffset(inspection.offset).also { builder ->
                                inspection.length?.let {
                                    builder.setLength(it)
                                }
                            }.build()
                    }
                ).build()
            )
        }

        return response.get()
    }
}
