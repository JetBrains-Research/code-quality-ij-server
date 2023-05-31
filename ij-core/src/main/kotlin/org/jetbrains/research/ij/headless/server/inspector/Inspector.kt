package org.jetbrains.research.ij.headless.server.inspector

import com.intellij.psi.PsiFile

interface Inspector {
    fun inspect(psiFile: PsiFile): List<AdaptedInspection>
}
