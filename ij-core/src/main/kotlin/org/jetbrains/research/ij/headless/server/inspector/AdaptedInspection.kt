package org.jetbrains.research.ij.headless.server.inspector

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemDescriptorUtil
import org.jetbrains.research.ij.headless.server.inspector.configs.BaseIJCodeInspectorConfig

data class AdaptedInspection(
    val inspector: String,
    val description: String,
    val lineNumber: Long,
    val offset: Long,
    val length: Long
) {
    companion object {
        fun build(
            inspection: LocalInspectionTool,
            descriptor: ProblemDescriptor,
            config: BaseIJCodeInspectorConfig,
            message: String? = null
        ) = AdaptedInspection(
            inspector = inspection.shortName,
            description = config.adaptMessage(
                inspection,
                getMessage(descriptor, message)
            ),
            lineNumber = descriptor.lineNumber.toLong(),
            offset = descriptor.psiElement?.textOffset?.toLong() ?: -1L,
            length = descriptor.psiElement?.textLength?.toLong() ?: -1L
        )

        private fun getMessage(descriptor: ProblemDescriptor, message: String?) =
            message ?: ProblemDescriptorUtil.renderDescriptionMessage(
                descriptor,
                descriptor.psiElement
            )

        private fun BaseIJCodeInspectorConfig.adaptMessage(inspection: LocalInspectionTool, message: String): String {
            val messagesToAdapt = this.inspectionIdToAdaptedMessages[inspection.id] ?: return message
            val initialMessageKey = messagesToAdapt.keys.find { it in message } ?: return message
            return messagesToAdapt[initialMessageKey]!!.buildMessageText(message)
        }
    }
}
