/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.model

import android.graphics.Bitmap

data class SearchSuggestion(
    val url: String,
    var title: String? = null,
    var icon: Bitmap? = null
)