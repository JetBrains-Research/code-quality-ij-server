package org.jetbrains.research.ij.headless.server.inspector.configs

import com.intellij.lang.annotation.ExternalAnnotator

abstract class BaseAnnotatorConfig {
    abstract val annotator: ExternalAnnotator<*, *>
    abstract val annotatorName: String

    open val ignoredInspectionIds: Set<String> = emptySet()
    open val inspectionIdToAdaptedMessages: Map<String, Map<String, AdaptedMessage>> = emptyMap()
}
