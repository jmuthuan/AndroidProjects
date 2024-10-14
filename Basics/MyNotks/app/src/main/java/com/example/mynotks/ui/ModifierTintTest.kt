package com.example.mynotks.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import com.example.mynotks.ui.SemanticProperties.tint

object SemanticProperties {
    val tint = SemanticsPropertyKey<String>("backgroundColor")
    var SemanticsPropertyReceiver.tint by tint
}
fun Modifier.testTint(tint: String) = semantics { this.tint = tint }