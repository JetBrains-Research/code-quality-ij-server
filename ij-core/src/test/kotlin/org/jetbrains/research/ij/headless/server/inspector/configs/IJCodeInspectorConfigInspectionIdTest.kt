package org.jetbrains.research.ij.headless.server.inspector.configs

import com.intellij.lang.Language
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.python.PythonLanguage
import org.jetbrains.research.ij.headless.server.inspector.getAllInspections
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.jetbrains.research.ij.headless.server.inspector.configs.python.PythonIJCodeInspectorConfig

@RunWith(Parameterized::class)
class IJCodeInspectorConfigInspectionIdTest : BasePlatformTestCase() {
    @JvmField
    @Parameterized.Parameter(0)
    var config: BaseIJCodeInspectorConfig? = null

    @JvmField
    @Parameterized.Parameter(1)
    var language: Language? = null

    private fun Set<String>.checkIds(allInspectionsIds: List<String>) {
        forEach {
            assert(it in allInspectionsIds) { "Inspection with id $it for language ${language!!.id} does not exist!" }
        }
    }

    @Test
    fun testIJCodeInspectorConfig() {
        requireNotNull(config) { "BaseIJCodeInspectorConfig was not passed!" }
        requireNotNull(language) { "Language was not passed!" }
        val allInspectionsIds = language!!.getAllInspections().map { it.id }
        config!!.ignoredInspectionIds.checkIds(allInspectionsIds)
        config!!.inspectionIdToDisabledMessages.keys.checkIds(allInspectionsIds)
        config!!.inspectionIdToAdaptedMessages.keys.checkIds(allInspectionsIds)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Config: {0}")
        fun getTestData() = listOf(
            arrayOf(
                PythonIJCodeInspectorConfig,
                PythonLanguage.getInstance()
            )
        )
    }
}
