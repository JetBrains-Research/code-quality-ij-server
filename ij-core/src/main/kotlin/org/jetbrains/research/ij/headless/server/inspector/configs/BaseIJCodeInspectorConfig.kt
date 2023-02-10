package org.jetbrains.research.ij.headless.server.inspector.configs

abstract class BaseIJCodeInspectorConfig {
    open val ignoredInspectionIds: Set<String> = emptySet()
    open val inspectionIdToDisabledMessages: Map<String, Set<String>> = emptyMap()
    open val inspectionIdToAdaptedMessages: Map<String, Map<String, String>> = emptyMap()
}
