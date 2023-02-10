package org.jetbrains.research.ij.headless.server.inspector.configs.python

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class IJCodeInspectorConfigDisableMessageTest : BasePlatformTestCase() {
    @JvmField
    @Parameterized.Parameter(0)
    var inspectionId: String? = null

    @JvmField
    @Parameterized.Parameter(1)
    var code: String? = null

    @JvmField
    @Parameterized.Parameter(2)
    var disabledMessage: String? = null

    @Test
    fun testIJCodeInspectorConfigDisableMessage() {
        requireNotNull(inspectionId) { "Inspection id was not passed!" }
        requireNotNull(code) { "Code was not passed!" }
        requireNotNull(disabledMessage) { "Disabled message was not passed!" }
        val adaptedInspections = getAdaptedInspections(myFixture, code!!, inspectionId!!).map { it.description }
        assert(disabledMessage !in adaptedInspections) { "Found disabled descriptor $disabledMessage for inspection $inspectionId for code${System.lineSeparator()}$code" }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "Inspection id: {0}, code: {1}")
        fun getTestData() = listOf(
            // BEST_PRACTICES
            arrayOf(
                "PyDataclass",
                """
import attr


@attr.s
class AttrFactory:
    x = attr.ib(default=attr.Factory(int))

    @x.default
    def __init_x__(self):
        return 1
                """.trimIndent(),
                "A default is set using"
            ),

            arrayOf(
                "PyDataclass",
                """
import attr


@attr.s
class A:
    x = attr.ib()

    @x.default
    def init_x2(self, attribute, value):
        return 10
                """.trimIndent(),
                "should take only"
            ),

            // ERROR_PRONE
            arrayOf(
                "PyFinal",
                """
from typing import overload
from typing_extensions import final

class B:
    @overload
    def foo(self, a: int) -> int: ...

    @final
    @overload
    def foo(self, a: str) -> str: ...
                """.trimIndent(),
                "'@final' should be placed on the implementation"
            )
        )
    }
}
