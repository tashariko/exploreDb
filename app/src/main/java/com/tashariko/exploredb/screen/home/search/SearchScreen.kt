package com.tashariko.exploredb.screen.home.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import com.tashariko.exploredb.theming.space18

@Composable
fun SearchScreen(modifier: Modifier){

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (text) = createRefs()
        Text(
            text = "Search Screen",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.constrainAs(text) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                centerVerticallyTo(parent)
            }
        )
    }
}