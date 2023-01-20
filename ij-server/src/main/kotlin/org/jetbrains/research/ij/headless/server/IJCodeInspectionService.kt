package org.jetbrains.research.ij.headless.server

import com.intellij.codeInspection.ProblemDescriptorUtil
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiFile
import com.jetbrains.python.PythonLanguage
import org.jetbrains.research.ij.headless.server.utils.createProject
import org.jetbrains.research.ij.headless.server.utils.createPsiFile
import org.jetbrains.research.ij.headless.server.utils.updatePsiFileContent
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference

class IJCodeInspectionService(templatesPath: Path) : CodeInspectionServiceGrpcKt.CodeInspectionServiceCoroutineImplBase() {

    private val logger = Logger.getInstance(javaClass)

    private val project = createProject(PythonLanguage.INSTANCE, templatesPath) ?: error("Can not create project!")
    private val dummyPsiFiles = mutableMapOf<Language, PsiFile>()

    override suspend fun inspect(request: Code): InspectionResult {
        logger.info("Receive request: $request")

        val languageId = request.languageId.name
        val language = Language.findLanguageByID(request.languageId.name)
            ?: error("No such language by id $languageId")
        val file = getDummyFile(language, request.text)

        val response = AtomicReference<InspectionResult>()
        ApplicationManager.getApplication().invokeAndWait {
            val result = IJCodeInspector.inspect(file)
            response.set(
                InspectionResult.newBuilder().addAllProblems(
                    result.flatMap { (inspection, descriptors) ->
                        descriptors.map { descriptor ->
                            Problem.newBuilder()
                                .setName(
                                    ProblemDescriptorUtil.renderDescriptionMessage(
                                        descriptor,
                                        descriptor.psiElement
                                    )
                                )
                                .setInspector(inspection.shortName)
                                .setLineNumber(descriptor.lineNumber.toLong())
                                .setOffset(descriptor.psiElement?.textOffset?.toLong() ?: -1L)
                                .setLength(descriptor.psiElement?.textLength?.toLong() ?: -1L)
                                .build()
                        }
                    }
                ).build()
            )
        }

        return response.get()
    }

    private fun getDummyFile(language: Language, text: String): PsiFile {
        logger.info("Get dummy file for language $language with text $text")

        return dummyPsiFiles.getOrPut(language) {
            logger.info("Start to create new psi file...")

            val file = AtomicReference<PsiFile>()
            ApplicationManager.getApplication().invokeAndWait {
                val psiFile = createPsiFile(project, "${language.id}_dummy", language, "")
                updatePsiFileContent(psiFile, text)
                file.set(psiFile)
            }

            logger.info("Finish to create new psi file...")
            file.get()
        }
    }
}
