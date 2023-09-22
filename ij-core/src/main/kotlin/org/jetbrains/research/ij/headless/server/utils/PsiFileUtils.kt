package org.jetbrains.research.ij.headless.server.utils

import com.intellij.lang.Language
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.idea.core.util.toPsiFile

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

fun findPsiFileByName(project: Project, fileName: String): PsiFile {
    val scope = GlobalSearchScope.allScope(project)

    val mainVirtualFile = FilenameIndex.getVirtualFilesByName(fileName, scope).firstOrNull() ?: run {
        error("Can not find the $fileName file in the ${project.name} project")
    }

    return mainVirtualFile.toPsiFile(project)
        ?: error("Can not convert the virtual file $mainVirtualFile to a PSI file")
}
