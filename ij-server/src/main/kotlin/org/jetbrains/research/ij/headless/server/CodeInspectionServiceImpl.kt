package org.jetbrains.research.ij.headless.server

import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.research.ij.headless.server.inspector.IJCodeInspector
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference

class CodeInspectionServiceImpl(templatesPath: Path) :
    CodeInspectionServiceGrpcKt.CodeInspectionServiceCoroutineImplBase() {

    private val logger = Logger.getInstance(javaClass)

    private val psiFileManager = PsiFileManager(templatesPath)

    override suspend fun inspect(request: Code): InspectionResult {
        logger.info("Receive request: $request")

        val languageId = request.languageId.name
        val language = Language.findLanguageByID(request.languageId.name)
            ?: error("No such language by id $languageId")
        val file = psiFileManager.getPsiFile(language, request.text)

        val response = AtomicReference<InspectionResult>()

        ApplicationManager.getApplication().invokeAndWait {
            val inspections = IJCodeInspector.inspect(file)
            response.set(
                InspectionResult.newBuilder().addAllProblems(
                    inspections.map { inspection ->
                        Problem.newBuilder()
                            .setName(inspection.description)
                            .setInspector(inspection.inspector)
                            .setLineNumber(inspection.lineNumber)
                            .setOffset(inspection.offset)
                            .setLength(inspection.length)
                            .build()
                    }
                ).build()
            )
        }

        return response.get()
    }
}
