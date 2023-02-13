package org.jetbrains.research.ij.headless.server.inspector.configs

object PythonIJCodeInspectorConfig : BaseIJCodeInspectorConfig() {
    override val ignoredInspectionIds: Set<String> = setOf(
        // BEST_PRACTICES
        "PyIncorrectDocstring",
        "PyMissingOrEmptyDocstring",
        "PySingleQuotedDocstring",
        "PyDocstringTypes",
        "PyMissingTypeHints",

        // COMPLEXITY

        // ERROR_PRONE
        "PyDunderSlots",
        "PyClassVar",
        "PyNamedTuple",
        "PyRedeclaration",
        "PyInitNewSignature",
        "PyPropertyDefinition",
        "PyCompatibility",
        "PyDeprecation",
        "PyProtocol",
        "PyTestParametrized",
        "PyInterpreter",
        "PyStubPackagesCompatibility",
        "PyStubPackagesAdvertiser",
        "PyPackages", // PyRelativeImportInspection

        // CODE_STYLE
        "PyInconsistentIndentation",
        "PyClassicStyleClass",
        "PyNonAsciiChar",
        "PyOldStyleClasses",
        "PyUnnecessaryBackslash",
        "PyMandatoryEncoding",
        "PyClassHasNoInit"
    )

    override val inspectionIdToDisabledMessages: Map<String, Set<String>> = mapOf(
        // BEST_PRACTICES
        "PyDataclass" to setOf(
            "A default is set using",
            "should take only"
        ),
        "PyProtectedMember" to setOf(
            "is not declared in __all__"
        ),

        // COMPLEXITY

        // ERROR_PRONE
        "PyFinal" to setOf(
            "'@final' should be placed on the implementation"
        ),

        // CODE_STYLE
        "PyPep8Naming" to setOf(
            "Constant variable imported as non-constant",
            "Lowercase variable imported as non-lowercase",
            "CamelCase variable imported as lowercase",
            "CamelCase variable imported as constant"
        )
    )

    override val inspectionIdToAdaptedMessages: Map<String, Map<String, AdaptedMessage>> = mapOf(
        // BEST_PRACTICES
        "PySimplifyBooleanCheck" to mapOf(
            "Expression can be simplified" to AdaptedMessage(
                suffix = ", e.g. `if a != False:` is the same with `if a:`"
            )
        ),
        "PyArgumentEqualDefault" to mapOf(
            "Argument equals to the default parameter value" to AdaptedMessage(
                suffix = ". You can delete the argument, the default parameter value will be used automatically."
            )
        ),
        "PyBroadException" to mapOf(
            "Too broad exception clause" to AdaptedMessage(
                toReuseDescription = false,
                prefix = "Please, specify the exception type, but avoid using too general exception `Exception`"
            )
        ),
        "PyDataclass" to mapOf(
            "not supported between instances of" to AdaptedMessage(
                suffix = ". You should add parameters into `dataclass` decorator: @dataclass(order=True)."
            ),
            "because it is declared as init-only" to AdaptedMessage(
                suffix = ". Please, don't call this attribute."
            ),
            "'order' should be False if the class defines one of order methods" to AdaptedMessage(
                suffix = ": __le__, __lt__, __gt__, __ge__"
            ),
            " is not allowed. Use 'default_factory'" to AdaptedMessage(
                suffix = ", e.g. 'a: list = field(default_factory=list)'"
            )
        ),
        "PyDictCreation" to mapOf(
            "This dictionary creation could be rewritten as a dictionary literal" to AdaptedMessage(
                suffix = ", e.g. dic = {'var': 1}"
            )
        ),
        "PyListCreation" to mapOf(
            "This list creation could be rewritten as a list literal" to AdaptedMessage(
                suffix = ", e.g. l = [1, 2]"
            )
        ),

        "PySetFunctionToLiteral" to mapOf(
            "Function call can be replaced with set literal" to AdaptedMessage(
                suffix = ", e.g. set([c, a, b]) is the same with {c, a, b}"
            )
        ),

        "PyProtectedMember" to mapOf(
            "Access to a protected member " to AdaptedMessage(
                suffix = " is not recommended"
            )
        ),

        "PyMethodMayBeStatic" to mapOf(
            " may be 'static'" to AdaptedMessage(
                suffix = ", because you don't use any properties of the class"
            )
        ),

        "PyChainedComparisons" to mapOf(
            "Simplify chained comparison" to AdaptedMessage(
                suffix = ", e.g 'if x >= a and x <= b:' is the same with 'a <= x <= b'"
            )
        ),

        // CODE_STYLE
        "PyAugmentAssignment" to mapOf(
            "Assignment can be replaced with an augmented assignment" to AdaptedMessage(
                suffix = ", e.g 'a = a + b' is the same with 'a += b'"
            )
        ),
        "PyTrailingSemicolon" to mapOf(
            "Trailing semicolon in the statement" to AdaptedMessage(
                toReuseDescription = false,
                prefix = "You don't need to use a semicolon here"
            )
        ),
        "PyStatementEffect" to mapOf(
            "Statement seems to have no effect" to AdaptedMessage(
                toReuseDescription = false,
                prefix = "You can delete this statement since it has no effect"
            )
        ),

        // ERROR_PRONE
        "PyDefaultArgument" to mapOf(
            "Default argument value is mutable" to AdaptedMessage(
                suffix = ", replace with None or any immutable type"
            )
        ),
        "PyCallingNonCallable" to mapOf(
            " is not callable" to AdaptedMessage(
                suffix = ", you cannot use ()"
            )
        ),
        "PyComparisonWithNone" to mapOf(
            "Comparison with None performed with equality operators" to AdaptedMessage(
                suffix = ": replace '==' to 'is' or '!=' to 'is not'"
            )
        ),
        "PyArgumentList" to mapOf(
            "Unexpected argument" to AdaptedMessage(
                suffix = ": check the number of arguments and their types"
            ),
            " unfilled" to AdaptedMessage(
                suffix = ". Specify a default value or fill a value"
            )
        )
    )
}
