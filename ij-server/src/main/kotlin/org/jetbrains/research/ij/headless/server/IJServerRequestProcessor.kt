package org.jetbrains.research.ij.headless.server


import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.research.ij.headless.server.utils.JsonUtils.requestAdapter
import org.jetbrains.research.ij.headless.server.utils.PsiUtils.createProject
import org.jetbrains.research.ij.headless.server.utils.PsiUtils.createPsiFile
import org.jetbrains.research.ij.headless.server.utils.PsiUtils.updatePsiFileContent
import java.io.ObjectInputStream
import java.net.Socket

class CodeServerRequestProcessor {

    private lateinit var project: Project
    private val dummyPsiFiles = mutableMapOf<Language, PsiFile>()

    init {
        ApplicationManager.getApplication().invokeAndWait {
            project = createProject()
        }
    }

    fun accept(socket: Socket) {
        val inputStream = socket.getInputStream()
        try {
            val objectInputStream = ObjectInputStream(inputStream)
            val code = requestAdapter.fromJson(objectInputStream.readObject().toString().also { println(it) })
            val language = Language.findLanguageByID(code.languageId)
                ?: error("Language ${code.languageId} is not supported by server.")

//        val result = AtomicReference<T>()
//        ApplicationManager.getApplication().invokeAndWait {
//            val psiFile = getDummyFile(language, code.text)
//            result.set(handler.handle(psiFile))
//        }
//
//        val response = AtomicReference<R>()
//        ApplicationManager.getApplication().runReadAction {
//            response.set(handler.toResponse(result.get()))
//        }
//
//        call.respond(response.get(), typeInfo<Class<R>>())

        } catch (e: Exception) {
            inputStream.close()
            println(e)
        }
    }

    private fun getDummyFile(language: Language, text: String): PsiFile {
        return dummyPsiFiles.getOrPut(language) {
            return createPsiFile(project, "${language.id}_dummy", language, "")
        }.also { updatePsiFileContent(it, text) }
    }
}
