/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.dev.videodownloader.model.SearchSuggestion

@Composable
fun BrowserTopBar(
    modifier: Modifier,
    url: String,
    homeUrl: String,
    @DrawableRes searchEngineIcon: Int,
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
            homeUrl,
            searchEngineIcon,
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

