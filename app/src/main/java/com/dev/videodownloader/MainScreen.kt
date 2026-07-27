/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader

import android.graphics.Bitmap
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.TravelExplore

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.dev.videodownloader.components.SearchSuggestion
import com.dev.videodownloader.components.TextIconButton
import com.dev.videodownloader.ui.browser.BrowserScreen
import com.dev.videodownloader.ui.browser.BrowserTopBar
import com.dev.videodownloader.ui.downloadsscreen.DownloadsScreen
import com.dev.videodownloader.ui.theme.screenModifier
import com.dev.videodownloader.ui.queuescreen.QueueScreen


@Composable
fun MainScreen(){
    var actualScreen by remember { mutableIntStateOf(0) }
    var isTopBarVisible by remember { mutableStateOf(true) }
    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current)
    var browserUrl by remember { mutableStateOf("https://google.com") }
    var browserPageTitle by remember { mutableStateOf("Google") }
    var pageLoadProgress by remember { mutableIntStateOf(0) }
    val suggestions = remember { mutableStateListOf<SearchSuggestion>() }
    val focusManager = LocalFocusManager.current
    var pageIcon by remember { mutableStateOf<Bitmap?>(null) }

    val screens = listOf<@Composable (paddingValues: PaddingValues) -> Unit>(
        { paddingValues ->
            BrowserScreen(
                Modifier.screenModifier(paddingValues, additionalPadding = 0.dp),
                browserUrl,
                {
                    pageLoadProgress = it
                },
                {
                    browserUrl = it
                },
                {
                    val actualSuggestion = suggestions.find { suggestion -> suggestion.url == browserUrl }
                    if (actualSuggestion != null){
                        actualSuggestion.title = it
                    }

                    browserPageTitle = it
                },
                {
                    pageIcon = it
                },
                {
                    isTopBarVisible = it
                }
            )
        },
        { QueueScreen(Modifier.screenModifier(it)) },
        { DownloadsScreen(Modifier.screenModifier(it)) }
    )

    val topBars = listOf<@Composable (Modifier) -> Unit>(
        { modifier ->
            BrowserTopBar(
                modifier,
                browserUrl,
                browserPageTitle,
                pageLoadProgress,
                suggestions
            ) {
                Log.d("Browser.Debug", "$it$ is a valid url? {URLUtil.isValidUrl(it)}")
                val textEnhanced = URLUtil.guessUrl(it).lowercase()
                var actualSuggestion = suggestions.find { suggestion ->
                    suggestion.url == textEnhanced
                }

                if (actualSuggestion == null) actualSuggestion = SearchSuggestion(textEnhanced)

                suggestions.remove(actualSuggestion)

                suggestions.add(0, actualSuggestion)
                if (suggestions.size > 10) {
                    suggestions.removeAt(suggestions.lastIndex)
                }

                focusManager.clearFocus()
                browserUrl = textEnhanced
            }
        },
        { modifier ->
            Text("Queue Topbar", modifier)
        },
        { modifier ->
            Text("Downloads Topbar", modifier)
        }
    )

    Scaffold(
        topBar = {
            AnimatedVisibility(
                isTopBarVisible,
                enter = slideInVertically { -it + statusBarHeight } + expandVertically(expandFrom = Alignment.Top) { statusBarHeight },
                exit = slideOutVertically { -it + statusBarHeight } + shrinkVertically(shrinkTowards = Alignment.Top) { statusBarHeight }
            ) {
                topBars[actualScreen](Modifier
                    .fillMaxWidth()
                    .background(Color(0xff111111))
                    .statusBarsPadding())
            }
        },
        bottomBar = {
        Row(Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color(0xff111111))
            .navigationBarsPadding(),
            Arrangement.SpaceAround
        ) {
            TextIconButton("Guia", Icons.Default.TravelExplore, Modifier.weight(1f)) {
                if (actualScreen == 0) { isTopBarVisible = !isTopBarVisible }
                actualScreen = 0
            }
            TextIconButton("Fila", Icons.Default.Downloading, Modifier.weight(1f)) {
                actualScreen = 1
            }
            TextIconButton("Downloads", Icons.Default.Folder, Modifier.weight(1f)) {
                actualScreen = 2
            }
        }
    }) {
        screens[actualScreen](it)
    }

}