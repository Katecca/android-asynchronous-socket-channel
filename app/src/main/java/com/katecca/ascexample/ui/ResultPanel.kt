package com.katecca.ascexample.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextOverflow
import com.katecca.ascexample.viewmodel.ResultPanelViewModel
import androidx.compose.runtime.getValue

@Composable
fun ResultPanel(resultPanelViewModel: ResultPanelViewModel) {

    val result: String by resultPanelViewModel.result.observeAsState("")

    Text(
        overflow = TextOverflow.Ellipsis,
        text = result + "\n"
    )
}