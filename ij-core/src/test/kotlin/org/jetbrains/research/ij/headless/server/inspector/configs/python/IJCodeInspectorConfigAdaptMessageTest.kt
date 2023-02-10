package org.jetbrains.research.ij.headless.server.inspector.configs.python

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.research.ij.headless.server.inspector.configs.PythonIJCodeInspectorConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class IJCodeInspectorConfigAdaptMessageTest : BasePlatformTestCase() {
    @JvmField
    @Parameterized.Parameter(0)
    var inspectionId: String? = null

    @JvmField
    @Parameterized.Parameter(1)
    var code: String? = null

    @JvmField
    @Parameterized.Parameter(2)
    var initialMessage: String? = null

    @JvmField
    @Parameterized.Parameter(3)
    var fullInitialMessage: String? = null

    @Test
    fun testIJCodeInspectorConfigAdaptMessage() {
        requireNotNull(inspectionId) { "Inspection id was not passed!" }
        requireNotNull(code) { "Code was not passed!" }
        requireNotNull(initialMessage) { "Initial message was not passed!" }
        val adaptedInspections = getAdaptedInspections(myFixture, code!!, inspectionId!!).map { it.description }
        PythonIJCodeInspectorConfig.inspectionIdToAdaptedMessages[inspectionId!!]?.let { adaptedMessages ->
            adaptedMessages[initialMessage]?.let {
                val adaptedMessage = it.buildMessageText(fullInitialMessage ?: initialMessage!!)
                assert(adaptedMessage in adaptedInspections) { "The adapted message \"$adaptedMessage\" must be shown for code${System.lineSeparator()}code" }
            } ?: error("Can not find initial message \"$initialMessage\" for inspection $inspectionId")
        } ?: error("Can not find inspection with id $inspectionId")
    }

    companion object {
        @Suppress("ForbiddenComment")
        @JvmStatic
        @Parameterized.Parameters(name = "Inspection id: {0}, code: {1}")
        fun getTestData() = listOf(
            // BEST_PRACTICES
            arrayOf(
                "PySimplifyBooleanCheck",
                """
b = 5
if b != False:
    print(1)
                """.trimIndent(),
                "Expression can be simplified",
                null
            ),

            arrayOf(
                "PyArgumentEqualDefault",
                """
def my_function(a: int = 2):
    print(a)


my_function(2)
                """.trimIndent(),
                "Argument equals to the default parameter value",
                null
            ),

            arrayOf(
                "PyBroadException",
                """
x = '6'
try:
    if x > 3:
        print('X is larger than 3')
except:
    print("Oops! x was not a valid number. Try again...")
                """.trimIndent(),
                "Too broad exception clause",
                null
            ),

            arrayOf(
                "PyDataclass",
                """
from dataclasses import dataclass


@dataclass
class A:
    x: int = 10


a = A(1)
b = A(2)
print(a < b)
                """.trimIndent(),
                "not supported between instances of",
                "'__lt__' not supported between instances of 'A'"
            )

            // TODO: Does not work in tests, but works in general
//            arrayOf(
//                "PyDataclass",
//                """
// from __future__ import annotations
// from dataclasses import dataclass, InitVar
//
//
// @dataclass
// class C:
//    i: int
//    init_only: InitVar[int | None] = None
//
//    def __post_init__(self, init_only):
//        if self.i is None and init_only is not None:
//            self.i = init_only
//
//
// c = C(10, init_only=5)
// print(c.init_only)
//                """.trimIndent(),
//                "because it is declared as init-only",
//                "'C' object could have no attribute 'init_only' because it is declared as init-only"
//            )
        )
    }
}
