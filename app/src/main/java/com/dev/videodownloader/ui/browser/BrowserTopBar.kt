/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.ui.browser

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dev.videodownloader.components.SearchSuggestion
import com.dev.videodownloader.components.SearchSuggestionItem
import com.dev.videodownloader.ui.theme.centerComposable

@Composable
fun BrowserTopBar(
    modifier: Modifier,
    url: String,
    pageTitle: String = "",
    pageLoadProgress: Int,
    suggestions: SnapshotStateList<SearchSuggestion>,
    setUrlFun: (url: String) -> Unit
) {
    var drawerOpen by remember { mutableStateOf(false) }

    Column {
        BrowserSearchBar(
            modifier
                .height(IntrinsicSize.Min)
                .zIndex(1f),
            url,
            pageTitle,
            pageLoadProgress,
            { drawerOpen = it },
            setUrlFun
        )

        BrowserSuggestionsDrawer(
            drawerOpen,
            suggestions,
            setUrlFun
        )
    }
}

@Composable
fun BrowserSearchBar(
    modifier: Modifier,
    url: String,
    pageTitle: String = "",
    pageLoadProgress: Int,
    onFocusFun: (Boolean) -> Unit,
    setUrlFun: (url: String) -> Unit
) {
    val state = rememberTextFieldState(url)
    var isFocused by remember { mutableStateOf(false) }
    val actualText = state.text.toString()

    val mustBe = if (pageTitle == "") url else pageTitle
    if (actualText != mustBe && !isFocused){
        state.edit {
            replace(0, length, mustBe)
        }
    }

    Row(modifier) {
        Text("3", Modifier.centerComposable(10.dp, 10.dp),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(
            url != "https://www.google.com/",
            enter = slideInHorizontally { -it } + expandHorizontally(expandFrom = Alignment.End),
            exit = slideOutHorizontally { -it } + shrinkHorizontally(shrinkTowards = Alignment.End)
        ) {
            IconButton({ setUrlFun("https://www.google.com/") }, modifier = Modifier.centerComposable(
                0.dp,
                10.dp
            )) {
                Icon(Icons.Default.Home, "Home: google.com")
            }
        }

        TextField(
            state = state,
            modifier = Modifier
                .basicMarquee()
                .weight(1f)
                .background(Color(0xff000000))
                .onFocusEvent {
                    isFocused = it.hasFocus
                    if (it.hasFocus){
                        state.edit {
                            replace(0, length, url)
                        }
                    }

                    onFocusFun(it.hasFocus)
                },

            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            onKeyboardAction = {
                setUrlFun(state.text.toString())
            }
        )

        AnimatedVisibility(
            pageLoadProgress != 100,
            enter = slideInHorizontally { -it } + expandHorizontally(expandFrom = Alignment.Start),
            exit = slideOutHorizontally { -it } + shrinkHorizontally(shrinkTowards = Alignment.Start)
        ) {
            Text("$pageLoadProgress%", modifier = Modifier.centerComposable(10.dp, 0.dp), textAlign = TextAlign.Center)
        }

        Icon(Icons.Default.MoreVert, "Why?", modifier = Modifier.centerComposable(10.dp, 10.dp))
    }
}

@Composable
fun BrowserSuggestionsDrawer(
    drawerOpen: Boolean,
    suggestions: SnapshotStateList<SearchSuggestion>,
    setUrlFun: (url: String) -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.layout {
            measurable, constraints ->
            val placeable = measurable.measure(constraints)
            layout(placeable.width, 0) {
                placeable.placeRelative(0, 0, zIndex = 0f)
            }
        },

        visible = drawerOpen,
        enter = slideInVertically { -it },
        exit = slideOutVertically { -it }
    ) {
        Column(
            Modifier
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Color(0xff111111))
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            for (suggestion in suggestions) {
                SearchSuggestionItem(suggestion, Modifier, {suggestions.remove(it)}) {
                    setUrlFun(it.url)
                }
            }
        }
    }
}