package latte.example.com.simplemusicplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_file_list.view.*

/**
 * [RecyclerView.Adapter] of [MediaItemData]s used by the [MediaItemFragment].
 */
class MediaListAdapter(private val mediaItemList: MutableList<MediaItemData>
                       , private val itemClickedListener: (MediaItemData) -> Unit) : RecyclerView.Adapter<MediaViewHolder>() {


    override fun getItemCount(): Int = mediaItemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_file_list, parent, false)
        return MediaViewHolder(view, itemClickedListener)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {

        holder.item = mediaItemList[position]
        holder.titleView.text = mediaItemList[position].title
        holder.artist.text = mediaItemList[position].artist

    }
}

class MediaViewHolder(view: View, itemClickedListener: (MediaItemData) -> Unit) : RecyclerView.ViewHolder(view) {

    val titleView: TextView = view.album_title
    val artist: TextView = view.album_artist

    var item: MediaItemData? = null

    init {
        view.setOnClickListener {
            item?.let { itemClickedListener(it) }
        }
    }
}
