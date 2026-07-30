/*
 * VideoDownloader
 * Copyright (C) 2026 Joel Bergue dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package com.dev.videodownloader.model

import androidx.annotation.DrawableRes
import com.dev.videodownloader.R

enum class SearchEngines(
    val displayName: String,
    val searchUrl: String,
    val homeUrl: String,
    @param:DrawableRes val icon: Int
) {
    GOOGLE("Google", "https://www.google.com/search?q=", "https://www.google.com/", R.drawable.google_icon),
    DUCKDUCKGO("DuckDuckGo", "https://duckduckgo.com/?q=", "https://duckduckgo.com/", R.drawable.duckduckgo_icon),
    BING("Microsoft Bing", "https://www.bing.com/search?q=", "https://www.bing.com/", R.drawable.bing_icon),
    ECOSIA("Ecosia", "https://www.ecosia.org/search?q=", "https://www.ecosia.org/", R.drawable.ecosia_icon),
    YAHOO("Yahoo!", "https://search.yahoo.com/search?p=", "https://search.yahoo.com/", R.drawable.yahoo_icon),
    BRAVE("Brave Search", "https://search.brave.com/search?q=", "https://search.brave.com/", R.drawable.brave_icon),
    STARTPAGE("StartPage", "https://www.startpage.com/sp/search?q=", "https://www.startpage.com/", R.drawable.startpage_icon)
}