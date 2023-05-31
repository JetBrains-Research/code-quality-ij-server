package org.jetbrains.research.ij.headless.server.inspector.configs.python

import com.jetbrains.python.validation.Pep8ExternalAnnotator
import org.jetbrains.research.ij.headless.server.inspector.configs.AdaptedMessage
import org.jetbrains.research.ij.headless.server.inspector.configs.BaseAnnotatorConfig

object PythonPep8AnnotatorConfig : BaseAnnotatorConfig() {
    override val ignoredInspectionIds: Set<String> = setOf(
        "W291", // trailing whitespaces
        "W292",  // no newline at end of file
        "W293",  // blank line contains whitespaces
        "W503",  // line break before binary operator
        "W605",  // Invalid escape sequence
        "C408",  // unnecessary (dict/list/tuple) call - rewrite as a literal
        "E800",  // commented out code
        "I101",  // order of imports within a line
        "I202",  // additional new line
        "Q000",
        "E301", "E302", "E303", "E304", "E305",  // problem with pre-written templates
        "E402",  // module level import not at top of file
        "I100",  // Import statements are in the wrong order
    )
    override val inspectionIdToAdaptedMessages: Map<String, Map<String, AdaptedMessage>> = mapOf()

    override val annotator = Pep8ExternalAnnotator()
    override val annotatorName = "PEP-8"
}
