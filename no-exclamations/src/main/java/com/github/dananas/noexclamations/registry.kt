package com.github.dananas.noexclamations

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.*
import org.jetbrains.uast.kotlin.KotlinUPostfixExpression
import java.util.*

class NoExclamations : IssueRegistry() {
    override val issues: List<Issue> = listOf(ISSUE_EXCLAMATION_USED)

    override val api: Int = CURRENT_API

    private companion object {
        private val ISSUE_EXCLAMATION_USED = Issue.create(
            "NoExclamations",
            "Must not use !! operator.",
            "It is recommended to resolve nullability other ways than using !! operator.",
            Category.CORRECTNESS,
            5,
            Severity.ERROR,
            Implementation(
                ExclamationsDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
            )
        )
    }

    class ExclamationsDetector : Detector(), Detector.UastScanner {
        override fun createUastHandler(context: JavaContext) = ExclamationsDetectorHandler(context)

        override fun getApplicableUastTypes() = listOf<Class<out UElement>>(UExpression::class.java)

        class ExclamationsDetectorHandler(private val context: JavaContext) : UElementHandler() {
            override fun visitExpression(node: UExpression) {
                val postfixExpression = node as? KotlinUPostfixExpression ?: return
                if (postfixExpression.operator.text != "!!") return
                context.report(
                    ISSUE_EXCLAMATION_USED,
                    node,
                    context.getNameLocation(node),
                    "Double exclamation used."
                )
            }
        }
    }
}