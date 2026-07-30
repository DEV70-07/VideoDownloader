/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dev.videodownloader.model.SearchSuggestion
import com.dev.videodownloader.ui.theme.iconForm
import com.dev.videodownloader.ui.theme.rounded


@Composable
fun SearchSuggestionItem(
    data: SearchSuggestion,
    modifier: Modifier,
    onRemoveClick: (SearchSuggestion) -> Unit,
    onCLick: (SearchSuggestion) -> Unit
) {

    Row(
        modifier
            .rounded(20.dp)
            .clickable {
                onCLick(data)
            }
            .background(Color(0xFF1C1C1C))
            .fillMaxWidth()
            .padding(10.dp)
            .height(IntrinsicSize.Min),

    ) {
        data.icon?.let {
            Image(
                it.asImageBitmap(),
                "Suggestion Icon",
                Modifier
                    .layout {
                        measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        layout(width = placeable.width, 0) {
                            placeable.placeRelative(0,0)
                        }
                    }
                    .iconForm(Color(0xff111111)),
                contentScale = ContentScale.Fit,
            )
        }
        Column(Modifier
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
            .weight(1f)
        ) {

            data.title?.let {
                Text(it, maxLines = 1, modifier = Modifier.basicMarquee())
            }

            Text(
                data.url,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton({ onRemoveClick(data) })  {
            Icon(Icons.Default.Close, "Delete Suggestion")
        }
    }
}