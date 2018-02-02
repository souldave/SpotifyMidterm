package com.example.davegilbier.spotify


import android.support.v7.widget.RecyclerView
import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.*
import com.example.davegilbier.spotify.Controller.MainActivity
import android.content.Intent

/**
 * Created by Dave Gilbier on 1/28/18.
 */
class SongAdapter(val songList: ArrayList<Songs>, val context: Context, val ma:MainActivity): RecyclerView.Adapter<SongAdapter.ViewHolder>(){

    var currentSong:Int = 0
    var mContext = context
    val allSongs:ArrayList<String>  =  ArrayList()
    companion object {
        val SONGLIST = "songlist"
        val SONGPOS = "songpos"
        val SONGPLAY = "songplay"
    }

    override fun getItemCount(): Int {
        return songList.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mLinear = itemView.findViewById(R.id.linearLayout) as LinearLayout
        val stitle = itemView.findViewById(R.id.stitle) as TextView
        val sartist = itemView.findViewById(R.id.sartist) as TextView
        val salbum = itemView.findViewById(R.id.salbum) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val song: Songs = songList[position]
        holder?.stitle?.text = song.song
        holder?.sartist?.text = song.singer
        holder?.salbum?.text = song.album
        if(song.stat==1)
        {
        }

        holder?.mLinear?.setOnClickListener {
            currentSong = position
            holder?.stitle?.setTextColor(Color.parseColor("#1DB954"));
            (0 until songList.size).mapTo(allSongs)
            {
                songList[it].song_path
            }

            try {
                val frag = SongFragment.newInstance(song.song, song.singer)
                ma.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_play, frag)
                        .commit()
            } catch (e: Exception) {
            }

            var sIntent = Intent(mContext, SongService::class.java)
            sIntent.putStringArrayListExtra(SONGLIST, allSongs)
            sIntent.action = SONGPLAY
            sIntent.putExtra(SONGPOS, position)
            mContext.startService(sIntent)
        }

    }

}