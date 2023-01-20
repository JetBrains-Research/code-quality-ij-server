package org.jetbrains.research.ij.headless.server

import kotlinx.serialization.Serializable
import java.nio.file.Path

@Serializable
data class IJCodeServerConfig(
    val port: Int,
    val templatesPath: String
)