package org.jetbrains.research.ij.headless.server.handlers

import com.intellij.psi.PsiFile

interface IJServerRequestHandler<T, R> {

    fun handle(psiFile: PsiFile): T

    fun toResponse(result: T): R
}
