package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.core.Page
import org.example.blogmultiplatform.components.OverflowSidePanel
import org.example.blogmultiplatform.components.SidePanel
import org.example.blogmultiplatform.util.Constants
import org.example.blogmultiplatform.util.isUserLoggedIn
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    isUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    var overflowMenuOpened by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .maxWidth(Constants.PAGE_WIDTH.px)
        ) {
            SidePanel(onMenuClick = {
                overflowMenuOpened = true
            })
            if (overflowMenuOpened) {
                OverflowSidePanel(onMenuClose = {
                    overflowMenuOpened = false
                })
            }
        }
    }
}
