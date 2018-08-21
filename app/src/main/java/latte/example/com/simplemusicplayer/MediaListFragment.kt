package latte.example.com.simplemusicplayer

import android.Manifest
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_file_list.*
import latte.example.com.simplemusicplayer.util.FileUtils.getMp3Files

val FILELISTFRAGMENT_TAG: String = "FileListFragment"

class FileListFragment : Fragment() {


    private val handler: Handler = Handler()
    private val RECORD_REQUEST_CODE: Int = 101

    private lateinit var player: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var listAdapter: MediaListAdapter

    companion object {
        fun newInstance(): FileListFragment {
            return FileListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeRequest()

        // Seek bar change listener
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    player.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        listAdapter = MediaListAdapter(getMp3Files(context!!), itemClickedListener = {
            if (::player.isInitialized && player.isPlaying) {
                player.stop()
                player.reset()
                player.release()
                handler.removeCallbacks(runnable)
            }
            player = MediaPlayer()
            player.setAudioAttributes(AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build())

            player.setDataSource(it.path)
            player.prepare() //player가 준비가 될 때까지 기다림
            player.start()

            tv_duration.text = getTime(player.seconds)
            tv_pass.text = getTime(player.currentSeconds)
            initializeSeekBar()


            player.setOnCompletionListener {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
            }
        })
        media_list.layoutManager = LinearLayoutManager(media_list.context)
        media_list.adapter = listAdapter

    }

    fun getTime(seconds: Int): String {
        val minute: Int = seconds / 60
        val seconds = seconds% 60
        var zero : String
        if(seconds<10)
            zero = "0"
        else
            zero = ""
        return "$minute:$zero$seconds"
    } 


    private fun makeRequest() {
        ActivityCompat.requestPermissions(activity!!,
                arrayOf(Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.INTERNET
                        , Manifest.permission.WAKE_LOCK),
                RECORD_REQUEST_CODE)
    }


    private fun initializeSeekBar() {
        seek_bar.max = player.seconds
        seek_bar.progress = 0

        runnable = Runnable {
            seek_bar.progress = player.currentSeconds

            tv_pass.text = getTime(player.currentSeconds)

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    // Extension property to get media player duration in seconds
    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }


    // Extension property to get media player current position in seconds
    val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }
}
