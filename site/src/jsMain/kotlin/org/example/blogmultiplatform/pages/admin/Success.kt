package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import org.example.blogmultiplatform.models.Theme
import org.example.blogmultiplatform.navigation.Screen
import org.example.blogmultiplatform.util.Constants
import org.example.blogmultiplatform.util.Res
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun Success() {
    val context = rememberPageContext()

    LaunchedEffect(Unit) {
        delay(5000)
        context.router.navigateTo(Screen.AdminCreate.route)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.margin(bottom = 24.px),
            src = Res.Icon.checkmark,
            description = "Checkmark Icon"
        )
        SpanText(
            modifier = Modifier
                .fontFamily(Constants.FONT_FAMILY)
                .fontSize(24.px),
            text = "Post Successfully Created!"
        )
        SpanText(
            modifier = Modifier
                .fontFamily(Constants.FONT_FAMILY)
                .color(Theme.HalfBlack.rgb)
                .fontSize(18.px),
            text = "Redirecting you back..."
        )
    }
}