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

        // COMPLEXITY

        // ERROR_PRONE
        "PyFinal" to setOf(
            "'@final' should be placed on the implementation"
        )

        // CODE_STYLE
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
            )
        )
    )
}
