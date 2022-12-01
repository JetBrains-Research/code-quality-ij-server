package org.jetbrains.research.ij.headless

import com.intellij.ide.impl.ProjectUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiRecursiveElementVisitor
import com.jetbrains.python.PythonLanguage
import com.jetbrains.python.psi.PyStringLiteralExpression
import java.nio.file.Path
import java.util.logging.Logger

class PythonGreetingCodeAnalyzer {

    private val log = Logger.getLogger(javaClass.name)

    fun run(greeting: String) {
        val project = ProjectUtil.openOrImport(Path.of("."))
        val factory = PsiFileFactory.getInstance(project)
        val code = "print(\'$greeting\')"
        val file = factory.createFileFromText("greeting", PythonLanguage.INSTANCE, code)

        log.info("Analysing code: $code")
        file.accept(
            object : PsiRecursiveElementVisitor() {
                override fun visitElement(element: PsiElement) {
                    (element as? PyStringLiteralExpression)?.let {
                        log.info("PyStringLiteralExpression: ${element.stringValue}")
                    }
                    super.visitElement(element)
                }
            }
        )
    }
}