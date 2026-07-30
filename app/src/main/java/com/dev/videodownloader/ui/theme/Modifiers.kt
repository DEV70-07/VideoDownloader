/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.screenModifier(paddingValues: PaddingValues = PaddingValues(), additionalPadding: Dp = 20.dp) =
    fillMaxSize()
        //.verticalScroll(rememberScrollState())
        .padding(paddingValues)
        .padding(horizontal = additionalPadding, vertical = additionalPadding)

@Composable
fun Modifier.rounded(
    topStart: Dp,
    topEnd: Dp,
    bottomEnd: Dp,
    bottomStart: Dp
) =
    clip(RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart))

@Composable
fun Modifier.rounded(
    topStartPercent: Int,
    topEndPercent: Int,
    bottomEndPercent: Int,
    bottomStartPercent: Int
) =
    clip(RoundedCornerShape(topStartPercent, topEndPercent, bottomEndPercent, bottomStartPercent))

@Composable
fun Modifier.rounded(percent: Int) =
    rounded(percent, percent, percent, percent)


@Composable
fun Modifier.rounded(size: Dp) =
    rounded(size, size, size, size)

@Composable
fun Modifier.centerComposable(startPadding: Dp = 0.dp, endPadding: Dp = 0.dp) =
    fillMaxHeight()
        .padding(start = startPadding, end = endPadding)
        .wrapContentHeight(align = Alignment.CenterVertically)

@Composable
fun Modifier.iconForm(backgroundColor: Color, borderRadius: Dp = 20.dp, externalPadding: Dp = 10.dp, internalPadding: Dp = 10.dp) =
    padding(end = externalPadding)
        .rounded(borderRadius)
        .background(backgroundColor)
        .padding(internalPadding)
        .aspectRatio(1f)
        .fillMaxHeight()


