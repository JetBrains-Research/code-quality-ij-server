package org.jetbrains.research.ij.headless.server.inspector.configs

data class AdaptedMessage(
    val toReuseDescription: Boolean = true,
    val prefix: String? = null,
    val suffix: String? = null
) {
    fun buildMessageText(originalMessage: String): String {
        val body = if (toReuseDescription) {
            originalMessage
        } else {
            ""
        }
        return "${prefix ?: ""}$body${suffix ?: ""}"
    }
}

open class BaseIJCodeInspectorConfig {
    open val ignoredInspectionIds: Set<String> = emptySet()
    open val inspectionIdToDisabledMessages: Map<String, Set<String>> = emptyMap()
    open val inspectionIdToAdaptedMessages: Map<String, Map<String, AdaptedMessage>> = emptyMap()
}
