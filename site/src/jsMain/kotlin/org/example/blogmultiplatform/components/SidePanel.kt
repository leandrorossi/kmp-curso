package org.example.blogmultiplatform.components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.models.Theme
import org.example.blogmultiplatform.navigation.Screen
import org.example.blogmultiplatform.styles.NavigationItemStyle
import org.example.blogmultiplatform.util.Constants
import org.example.blogmultiplatform.util.Id
import org.example.blogmultiplatform.util.logout
import org.example.blogmultiplatform.util.Res
import org.jetbrains.compose.web.css.*

@Composable
fun SidePanel(onMenuClick: () -> Unit) {
    val breakpoint = rememberBreakpoint()
    if (breakpoint > Breakpoint.MD) {
        SidePanelInternal()
    } else {
        CollapsedSidePanel(onMenuClick = onMenuClick)
    }
}


@Composable
private fun SidePanelInternal() {
    Column(
        modifier = Modifier
            .padding(leftRight = 40.px, topBottom = 50.px)
            .width(Constants.SIDE_PANEL_WIDTH.px)
            .height(100.vh)
            .position(Position.Fixed)
            .backgroundColor(Theme.Secondary.rgb)
            .zIndex(9)
    ) {
        Image(
            modifier = Modifier.margin(bottom = 60.px),
            src = Res.Image.logo,
            description = "Logo Image"
        )
        NavigationItems()
    }
}

@Composable
private fun NavigationItems() {
    val context = rememberPageContext()
    SpanText(
        modifier = Modifier
            .margin(bottom = 30.px)
            .fontFamily(Constants.FONT_FAMILY)
            .fontSize(14.px)
            .color(Theme.HalfWhite.rgb),
        text = "Dashboard"
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Home",
        selected = context.route.path == Screen.AdminHome.route,
        icon = Res.PathIcon.home,
        onClick = {
            context.router.navigateTo(Screen.AdminHome.route)
        }
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Create Post",
        selected = context.route.path == Screen.AdminCreate.route,
        icon = Res.PathIcon.create,
        onClick = {
            context.router.navigateTo(Screen.AdminCreate.route)
        }
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "My Posts",
        selected = context.route.path == Screen.AdminMyPosts.route,
        icon = Res.PathIcon.posts,
        onClick = {
            context.router.navigateTo(Screen.AdminMyPosts.route)
        }
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Logout",
        icon = Res.PathIcon.logout,
        onClick = {
            logout()
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    )
}

@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title: String,
    icon: String,
    onClick: () -> Unit
) {
    Row(
        modifier = NavigationItemStyle.toModifier()
            .then(modifier)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        VectorIcon(
            modifier = Modifier.margin(right = 10.px),
            pathData = icon,
            selected = selected
        )
        SpanText(
            modifier = Modifier
                .id(Id.navigationText)
                .fontFamily(Constants.FONT_FAMILY)
                .fontSize(16.px)
                .thenIf(
                    condition = selected,
                    other = Modifier.color(Theme.Primary.rgb)
                ),
            text = title
        )
    }
}

@Composable
private fun VectorIcon(
    modifier: Modifier = Modifier,
    pathData: String,
    selected: Boolean
) {
    Svg(
        attrs = modifier
            .id(Id.svgParent)
            .width(24.px)
            .height(24.px)
            .toAttrs {
                attr("viewBox", "0 0 24 24")
                attr("fill", "none")
            }
    ) {
        Path(
            attrs = Modifier
                .id(Id.vectorIcon)
                .thenIf(
                    condition = selected,
                    other = Modifier.styleModifier {
                        property("stroke", Theme.Primary.hex)
                    }
                )
                .toAttrs {
                    attr("d", pathData)
                    attr("stroke-width", "2")
                    attr("stroke-linecap", "round")
                    attr("stroke-linejoin", "round")
                }
        )
    }
}

@Composable
fun CollapsedSidePanel(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Constants.COLLAPSED_PANEL_HEIGHT.px)
            .padding(leftRight = 24.px)
            .backgroundColor(Theme.Secondary.rgb),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaBars(
            modifier = Modifier
                .margin(right = 24.px)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick { onMenuClick() },
            size = IconSize.XL
        )
        Image(
            modifier = Modifier.width(80.px),
            src = Res.Image.logo,
            description = "Logo Image"
        )
    }
}

@Composable
fun OverflowSidePanel(onMenuClose: () -> Unit) {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()

    var translateX by remember { mutableStateOf((-100).percent) }
    var opacity by remember { mutableStateOf(0.percent) }

    LaunchedEffect(key1 = breakpoint) {
        translateX = 0.percent
        opacity = 100.percent
        if (breakpoint > Breakpoint.MD) {
            scope.launch {
                translateX = (-100).percent
                opacity = 0.percent
                delay(500)
                onMenuClose()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9)
            .opacity(opacity)
            .transition(CSSTransition(property = "opacity", duration = 300.ms))
            .backgroundColor(Theme.HalfBlack.rgb),
    ) {
        Column(
            modifier = Modifier
                .padding(all = 24.px)
                .fillMaxHeight()
                .width(if (breakpoint < Breakpoint.MD) 50.percent else 25.percent)
                .translateX(translateX)
                .transition(CSSTransition(property = "opacity", duration = 300.ms))
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .backgroundColor(Theme.Secondary.rgb)
        ) {
            Row(
                modifier = Modifier.margin(bottom = 60.px, top = 24.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(
                    modifier = Modifier
                        .margin(right = 20.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick {
                            scope.launch {
                                translateX = (-100).percent
                                opacity = 0.percent
                                delay(500)
                                onMenuClose()
                            }
                        },
                    size = IconSize.LG
                )
                Image(
                    modifier = Modifier.width(80.px),
                    src = Res.Image.logo,
                    description = "Logo Image"
                )
            }
            NavigationItems()
        }
    }
}