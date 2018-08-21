package latte.example.com.simplemusicplayer.util

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import latte.example.com.simplemusicplayer.MediaItemData

object FileUtils {
    fun getMp3Files(context: Context): MutableList<MediaItemData> {
        val fileList = mutableListOf<MediaItemData>()
        val projection = arrayOf(MediaStore.Audio.Media._ID
                , MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM
                , MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA)


        val cursor: Cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)
        while (cursor.moveToNext()) {
            fileList.add(MediaItemData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))));
        }
        cursor.close()
        return fileList
    }



}