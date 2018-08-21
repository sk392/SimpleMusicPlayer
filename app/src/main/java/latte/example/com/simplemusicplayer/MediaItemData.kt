package latte.example.com.simplemusicplayer

import android.net.Uri


data class MediaItemData(
        val id: String,
        val path: String,
        val title: String,
        val albumArt: String,
        val artist: String)
