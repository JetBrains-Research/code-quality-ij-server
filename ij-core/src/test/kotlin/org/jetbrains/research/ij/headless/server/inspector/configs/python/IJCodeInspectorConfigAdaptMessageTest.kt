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
        @Suppress("ForbiddenComment", "LongMethod")
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
            ),

            arrayOf(
                "PyDictCreation",
                """
dic = {}
dic['var'] = 1
                """.trimIndent(),
                "This dictionary creation could be rewritten as a dictionary literal",
                null
            ),

            arrayOf(
                "PyListCreation",
                """
l = [1]
l.append(2)
                """.trimIndent(),
                "This list creation could be rewritten as a list literal",
                null
            ),

            arrayOf(
                "PyProtectedMember",
                """
class A:
  def __init__(self):
    self._a = 1

  def foo(self):
    self.b= 1


print(A()._a)
                """.trimIndent(),
                "Access to a protected member ",
                "Access to a protected member _a of a class"
            ),

            arrayOf(
                "PyMethodMayBeStatic",
                """
class MyClass(object):
    def my_method(self, x):
        print(x)
                """.trimIndent(),
                " may be 'static'",
                "Method 'my_method' may be 'static'"
            ),

            arrayOf(
                "PyChainedComparisons",
                """
def do_comparison(x):
    xmin = 10
    xmax = 100
    if x >= xmin and x <= xmax:
        pass
                """.trimIndent(),
                "Simplify chained comparison",
                null
            )

        )
    }
}
