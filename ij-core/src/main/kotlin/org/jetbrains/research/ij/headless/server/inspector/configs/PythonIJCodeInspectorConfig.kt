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
        "PyClassHasNoInit",
    )

    override val inspectionIdToDisabledMessages: Map<String, Set<String>> = mapOf(
        // BEST_PRACTICES
        "PyDataclass" to setOf(
            "A default is set using", "should take only"
        ),

        // COMPLEXITY

        // ERROR_PRONE
        "PyFinal" to setOf(
            "'@final' should be placed on the implementation"
        ),

        // CODE_STYLE
    )

    override val inspectionIdToAdaptedMessages: Map<String, Map<String, String>> = mapOf(
        // BEST_PRACTICES
        "PySimplifyBooleanCheck" to mapOf(
            "Expression can be simplified" to "Expression can be simplified, e.g. `if a != False:` is the same with `if a:`"
        ), "PyArgumentEqualDefault" to mapOf(
            "Argument equals to the default parameter value" to "Argument equals to the default parameter value. You can delete the argument, the default parameter value will be used automatically."
        )
    )
}
