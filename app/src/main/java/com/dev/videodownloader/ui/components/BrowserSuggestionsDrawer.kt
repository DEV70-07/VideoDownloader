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
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.dev.videodownloader.model.SearchSuggestion

@Composable
fun BrowserSuggestionsDrawer(
    drawerOpen: Boolean,
    suggestions: SnapshotStateList<SearchSuggestion>,
    setUrlFun: (url: String) -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.layout { measurable, constraints ->
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
                SearchSuggestionItem(suggestion, Modifier.Companion, { suggestions.remove(it) }) {
                    setUrlFun(it.url)
                }
            }
        }
    }
}