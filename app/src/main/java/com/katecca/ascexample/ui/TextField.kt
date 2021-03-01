package com.katecca.ascexample.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun TextField(
    label: String,
    textState: MutableState<TextFieldValue>,
    modifier: Modifier) {
    TextField(
        label = { Text(text = label) },
        value = textState.value,
        onValueChange = {textState.value = it},
        modifier = modifier
    )
}