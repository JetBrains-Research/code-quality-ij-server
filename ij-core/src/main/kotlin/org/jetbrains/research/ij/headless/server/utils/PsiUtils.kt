package org.jetbrains.research.ij.headless.server.utils

import com.intellij.ide.impl.ProjectUtil
import com.intellij.lang.Language
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import java.nio.file.Path

object PsiUtils {

    fun updatePsiFileContent(psiFile: PsiFile, text: String) {
        val document = psiFile.viewProvider.document
            ?: error("No document for file found")
        WriteAction.run<Throwable> {
            document.setText(text)
        }
        PsiDocumentManager.getInstance(psiFile.project).commitDocument(document)
    }


    fun createPsiFile(project: Project, fileName: String, language: Language, text: String = ""): PsiFile {
        return PsiFileFactory.getInstance(project).createFileFromText(fileName, language, text)
            ?: error("Can not create file $fileName")
    }

    fun createProject(path: Path = Path.of(".")): Project {
        return ProjectUtil.openOrImport(path)
    }
}
