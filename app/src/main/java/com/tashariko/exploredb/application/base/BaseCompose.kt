package com.tashariko.exploredb.application.base

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tashariko.exploredb.theming.AppTheme

@Composable
fun AppCompose(content: @Composable () -> Unit) {
    AppTheme {
        Surface {
            content()
        }
    }
}