package org.jetbrains.research.ij.headless.server.utils

import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.copyToRecursively
import kotlin.io.path.createDirectories

@OptIn(ExperimentalPathApi::class)
fun copyDirectory(sourcePath: Path, destPath: Path) {
    sourcePath.copyToRecursively(
        destPath.apply { parent?.createDirectories() },
        followLinks = false
    )
}
