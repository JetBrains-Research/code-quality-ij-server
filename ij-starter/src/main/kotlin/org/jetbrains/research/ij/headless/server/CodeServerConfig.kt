package org.jetbrains.research.ij.headless.server

import kotlinx.serialization.Serializable

@Serializable
data class CodeServerConfig(
    val port: Int,
    val templatesPath: String,
    val languages: List<String>
)
