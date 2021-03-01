package com.katecca.ascexample.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CmdButton(label: String, enabled: Boolean = true, onClick: () -> Unit = {}) {
    androidx.compose.material.Button(
            onClick = onClick,
            modifier = Modifier.padding(5.dp),
            enabled = enabled) {
        Text(text = label)
    }
}