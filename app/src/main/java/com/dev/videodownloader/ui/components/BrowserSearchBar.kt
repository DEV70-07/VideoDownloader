/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.videodownloader.ui.theme.centerComposable
import com.dev.videodownloader.ui.theme.rounded

@Composable
fun BrowserSearchBar(
    modifier: Modifier,
    url: String,
    homeUrl: String,
    searchEngineIcon: Int,
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
        Text(
            "3", Modifier.centerComposable(10.dp, 10.dp),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(
            url != homeUrl,
            enter = slideInHorizontally { -it } + expandHorizontally(expandFrom = Alignment.End),
            exit = slideOutHorizontally { -it } + shrinkHorizontally(shrinkTowards = Alignment.End)
        ) {
            IconButton(
                { setUrlFun(homeUrl) }, modifier = Modifier.centerComposable(
                    0.dp,
                    5.dp
                )
            ) {
                Icon(
                    painterResource(searchEngineIcon),
                    "Home: $homeUrl",
                    //modifier = Modifier.iconForm(Color(0xff222222), externalPadding = 5.dp, internalPadding = 5.dp),
                    Modifier.background(Color(0xFF333333)).padding(2.dp).rounded(100),
                    tint = Color.Unspecified
                )
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
                    if (it.hasFocus) {
                        state.edit {
                            replace(0, length, url)
                        }
                    }

                    onFocusFun(it.hasFocus)
                },

            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            onKeyboardAction = {
                setUrlFun(state.text.toString())
            }
        )

        AnimatedVisibility(
            pageLoadProgress != 100,
            enter = slideInHorizontally { -it } + expandHorizontally(expandFrom = Alignment.Start),
            exit = slideOutHorizontally { -it } + shrinkHorizontally(shrinkTowards = Alignment.Start)
        ) {
            Text(
                "$pageLoadProgress%",
                modifier = Modifier.centerComposable(10.dp, 0.dp),
                textAlign = TextAlign.Center
            )
        }

        Icon(Icons.Default.MoreVert, "Why?", modifier = Modifier.centerComposable(10.dp, 10.dp))
    }
}