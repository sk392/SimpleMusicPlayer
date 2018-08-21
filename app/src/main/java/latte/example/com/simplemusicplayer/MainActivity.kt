package latte.example.com.simplemusicplayer

import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragment: FileListFragment? = supportFragmentManager.findFragmentByTag(FILELISTFRAGMENT_TAG) as FileListFragment?

        if (fragment == null) {
            fragment = FileListFragment.newInstance()

            supportFragmentManager.beginTransaction()
                    .apply { replace(R.id.content_layout, fragment, FILELISTFRAGMENT_TAG) }.commit()
        }
    }
}

