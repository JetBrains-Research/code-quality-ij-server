package org.jetbrains.research.ij.headless.server.inspector.configs.python

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.jetbrains.python.PythonLanguage
import org.jetbrains.research.ij.headless.server.inspector.AdaptedInspection
import org.jetbrains.research.ij.headless.server.inspector.IJCodeInspector
import org.jetbrains.research.ij.headless.server.inspector.configs.PythonIJCodeInspectorConfig

fun getAdaptedInspections(fixture: CodeInsightTestFixture, code: String, inspectionId: String): List<AdaptedInspection> {
    val psi = fixture.configureByText("dummy.py", code)
    val allInspections = IJCodeInspector.getInspections(PythonLanguage.getInstance(), PythonIJCodeInspectorConfig)
    val inspection =
        allInspections.find { it.id == inspectionId } ?: error("Can not find inspection with id $inspectionId")
    return IJCodeInspector.inspectSingleAndBuildAdaptedInspection(psi, inspection, PythonIJCodeInspectorConfig)
}
